# Ably Asset Tracking Demo: Android Rider

_[Ably](https://ably.com) is the platform that powers synchronized digital experiences in realtime. Whether attending an event in a virtual venue, receiving realtime financial information, or monitoring live car performance data – consumers simply expect realtime digital experiences as standard. Ably provides a suite of APIs to build, extend, and deliver powerful digital experiences in realtime for more than 250 million devices across 80 countries each month. Organizations like Bloomberg, HubSpot, Verizon, and Hopin depend on Ably’s platform to offload the growing complexity of business-critical realtime data synchronization at global scale. For more information, see the [Ably documentation](https://ably.com/documentation)._

## Overview

This demo presents a mock delivery Rider app with functionality matching that expected for the typical use case for
[Ably's Asset Tracking solution](https://ably.com/solutions/asset-tracking),
being the publication of Location updates for a delivery being made to a customer,
where the Rider is using an Android device.
Such deliveries could be food, groceries or other packages ordered for home delivery.

This app is a member of our
[suite of **Ably Asset Tracking Demos**](https://github.com/ably/asset-tracking-demos),
developed by Ably's SDK Team to demonstrate best practice for adopting and deploying Asset Tracking.

## Running the example

To build these apps from source you will need to specify credentials in Gradle properties.

The following secrets need to be injected into Gradle by storing them in `gradle.properties` file in
the project root:

- `ABLY_API_KEY`: On your [Ably accounts page](https://ably.com/accounts/), select your application,
  and paste an API Key from the API Keys tab (with relevant capabilities for either subscriber/
  publisher). This API key needs the following capabilities: `publish`, `subscribe`, `history`
  and `presence`.
- `MAPBOX_DOWNLOADS_TOKEN`: On
  the [Mapbox Access Tokens page](https://account.mapbox.com/access-tokens/), create a token with
  the `DOWNLOADS:READ` secret scope.
- `MAPBOX_ACCESS_TOKEN`: On
  the [Mapbox Access Tokens page](https://account.mapbox.com/access-tokens/), create a token with
  public scopes.

To do this, create a file in the project root (if it doesn't exist already) named `local.properties`
, and add the following values:

```bash
ABLY_API_KEY=get_value_from_ably_dashboard
MAPBOX_DOWNLOADS_TOKEN=create_token_with_downloads_read_secret_scope
MAPBOX_ACCESS_TOKEN=create_token_with_downloads_read_secret_scope
```

## Resolutions

The app is configured to publish the updates with variable frequency depending on device battery level, distance to the destination, and subscriber presence. For more information, see the [Ably Asset Tracking SDKs for Android  documentation](https://github.com/ably/ably-asset-tracking-android#resolution-policies).


### Default publisher resolution

Those properties are used as a fallback, when no other configuration is provided

| property            |   value  |
|---------------------|:--------:|
| accuracy            | BALANCED |
| desiredInterval     |   1000   |
| minimumDisplacement |  1000.0  |


### Trackable resolution constraints

When creating new asset to track, we are providing a more detailed resolution configuration. Those can vary between the assets, this demo app uses the following configuration for every asset.

| property              |       value      |
|-----------------------|:----------------:|
| proximityThreshold    | spatial = 1000.0 |
| batteryLevelThreshold |       50.0       |
| lowBatteryMultiplier  |        5.0       |
| resolutions           |     see below    |


#### Trackable resolution set

accuracy - one of the enum values defined in the SDK, other available values are `LOW`, `MAXIMUM`.

|      | withoutSubscriber | withSubscriber |
|------|:-----------------:|:--------------:|
| far  |      MINIMUM      |    BALANCED    |
| near |      BALANCED     |      HIGH      |

minimumDisplacement provided in meters

|      | withoutSubscriber | withSubscriber |
|------|:-----------------:|:--------------:|
| far  |       100.0       |      10.0      |
| near |        10.0       |       1.0      |

desiredInterval provided in milliseconds

|      | withoutSubscriber | withSubscriber |
|------|:-----------------:|:--------------:|
| far  |        2000       |      1000      |
| near |        1000       |       500      |

## Location logs

To export location logs from the current session, enter the settings screen by clicking the cog icon on the main screen. Logs for each session are stored in two files named by time and date of the first location logged, for example, `14_07_07:06:16_location.log`. Note that location logging starts after adding a trackable.
- `*_location.log` - contains location reading. Consequent readings are appended to the file as soon as they arrive
- `*__history.log` - contains location history from the session and is available only after the publisher disconnects

There are the following options available on the settings menu:
- "Close session and save logs" - disconnects current publisher and exports
- "Export logs" - shares all files present in the log directory inside the app's private storage
- "Remove logs" - removes all files present in the log directory inside the app's private storage
- "Start session" - creates a new publisher and connects it to Ably
- "Restart application" - restarts the application process. Will restart all publishers as well as background services

## Known Limitations

For the sake of simplicity, the demo app does not handle the following cases:

- keeping Wi-Fi connection awake - when a phone with the rider app running has a locked screen, the OS will disable the Wi-Fi connection to preserve the battery power after some time. If the phone has no other connection to the internet, the tracking will stop. It will resume automatically once the connection is re-established
