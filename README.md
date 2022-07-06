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

## Limitations

For the sake of simplicity, the demo app does not handle the following cases:

- keeping Wi-Fi connection awake - when a phone with the rider app running has a locked screen, the OS will disable the Wi-Fi connection to preserve the battery power after some time. If the phone has no other connection to the internet, the tracking will stop. It will resume automatically once the connection is re-established