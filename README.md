# @exodus/react-native-fast-image

Exodus's fork of [@d11/react-native-fast-image](https://github.com/ds-horizon/react-native-fast-image), itself a fork of [react-native-fast-image](https://github.com/DylanVann/react-native-fast-image). All credit goes to the original authors.

FastImage is a React Native image component with native caching, built on [SDWebImage](https://github.com/SDWebImage/SDWebImage) (iOS) and [Glide](https://github.com/bumptech/glide) (Android), with support for the Fabric renderer and TurboModules.

## Security note (trusted images only)

`@exodus/react-native-fast-image` MUST NOT be used with untrusted or user-influenced image URLs (including deep links, external API responses, NFTs, or any content a user can control/redirect).

The native decoders parse whatever bytes the URL returns, so only load images from sources you trust.

## How this fork differs from upstream

This fork is based on upstream v8.13.0 and changes it as follows:

- The AVIF and SVG decoders are removed on both platforms, as are the WebP decoders (`SDWebImageWebPCoder` on iOS, the Glide WebP decoder on Android) and the penfeizhou animation plugin for Glide. Only SDWebImage's and Glide's built-in decoders run.
- `FastImage.preload()` is removed.
- Android fixes: a crash when only `defaultSource` is set, thread-safety bugs in the progress listeners and the view registry, a key mismatch that left progress listeners behind when a view was dropped, `clearDiskCache()` clearing Glide's disk cache on the main thread, and invalid values in progress events.
- iOS fixes: a local image that can't be decoded now fires `onError` instead of rendering blank, and a missing image is no longer set on the view.

## Installation

```bash
npm install @exodus/react-native-fast-image
cd ios && pod install
```

## Usage

```jsx
import FastImage from '@exodus/react-native-fast-image'
import * as React from 'react'

const YourImage = () => (
  <FastImage
    style={{ width: 200, height: 200 }}
    source={{
      uri: 'https://example.com/image.png',
      headers: { Authorization: 'someAuthToken' },
      priority: FastImage.priority.normal,
    }}
    resizeMode={FastImage.resizeMode.contain}
  />
)
```

## Already using Glide with an `AppGlideModule`?

If your app already uses Glide with an `AppGlideModule`, [read this guide](docs/app-glide-module.md).

## ProGuard

If you use [ProGuard](https://www.guardsquare.com/proguard), add these rules to `android/app/proguard-rules.pro`:

```pro
-keep public class com.dylanvann.fastimage.* {*;}
-keep public class com.dylanvann.fastimage.** {*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
```

## API

### Properties

| Property | Type | Description |
| --- | --- | --- |
| `source` | `object` | Source for the remote image. Accepts an object with `uri`, `headers`, `priority` and `cache`. |
| `source.uri` | `string` | The URL to load the image from. |
| `source.headers` | `object` | Headers to load the image with, e.g. `{ Authorization: "someAuthToken" }`. |
| `source.priority` | `FastImage.priority` | Load priority: `FastImage.priority.low`, `FastImage.priority.normal` **(default)** or `FastImage.priority.high`. |
| `source.cache` | `FastImage.cacheControl` | Cache control: `FastImage.cacheControl.immutable` **(default)**, `FastImage.cacheControl.web` or `FastImage.cacheControl.cacheOnly`. |
| `defaultSource` | `number` | An asset loaded with `require()` or `import`. On Android, `defaultSource` does not work in debug mode. |
| `resizeMode` | `FastImage.resizeMode` | Resize mode: `FastImage.resizeMode.contain`, `FastImage.resizeMode.cover` **(default)**, `FastImage.resizeMode.stretch` or `FastImage.resizeMode.center`. |
| `transition` | `FastImage.transition` | Transition when the image appears: `FastImage.transition.none` **(default)** or `FastImage.transition.fade` (like React Native's `Image`). |
| `onLoadStart` | `function` | Called when the image starts to load. |
| `onProgress` | `(event: OnProgressEvent) => void` | Called while the image loads, with `event.nativeEvent.loaded` and `event.nativeEvent.total` bytes. |
| `onLoad` | `(event: OnLoadEvent) => void` | Called when the image loads, with `event.nativeEvent.width` and `event.nativeEvent.height`. |
| `onError` | `(event: OnErrorEvent) => void` | Called when the image fails to load or the source is malformed, with `event.nativeEvent.error`. |
| `onLoadEnd` | `function` | Called when the image finishes loading, whether it succeeded or failed. |
| `style` | `ViewStyle` | Style for the image component, including `borderRadius`. |
| `fallback` | `boolean` | If `true`, falls back to React Native's `Image`, with the same styles and layout. |
| `tintColor` | `number` or `string` | Color tint for all non-transparent pixels in the image. |
| `blurRadius` | `number` | Blur radius applied to the image. |
| `testID` | `string` | ID for tests, such as with [`react-test-renderer`](https://www.npmjs.com/package/react-test-renderer). |

### Static methods

| Method | Description |
| --- | --- |
| `FastImage.clearMemoryCache(): Promise<void>` | Clears all images from the memory cache. |
| `FastImage.clearDiskCache(): Promise<void>` | Clears all images from the disk cache. |

## Troubleshooting

See the [troubleshooting guide](docs/troubleshooting.md).

## Credits

The original idea for this module came from [@vovkasm](https://github.com/vovkasm)'s [react-native-web-image](https://github.com/vovkasm/react-native-web-image). The component was created by [Dylan Vann](https://github.com/DylanVann) and is maintained upstream by [Dream Sports Labs](https://github.com/ds-horizon).

## License

- **FastImage**: MIT © [Dream Sports Labs](https://github.com/ds-horizon)
- **SDWebImage**: MIT
- **Glide**: BSD, part MIT and Apache 2.0. See its [LICENSE](https://github.com/bumptech/glide/blob/master/LICENSE).
