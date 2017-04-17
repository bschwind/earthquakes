# Earthquakes

An app to display recent earthquake information on a map, implemented with Re-Natal, a react-native wrapper for ClojureScript.

Initialize Project
------------------

```
re-natal init earthquakes -i reagent6
cd earthquakes
```

## Adding npm libraries

* Add the dependency to `package.json`
* Add the npm module name to the `dependencies` array in `.re-natal`
* Run `npm install && re-natal deps`
* Restart the Figwheel server

## Installing AirBnB's Map Component

* Get some alcohol
* `npm install react-native-maps --save` // I got version 0.14.0
* react-native link react-native-maps
* I had to manually edit `node_modules/react-native-maps/lib/ios/AirMaps.xcodeproj/project.pbxproj` and change all references from `RCTConvert+MapKit.m` to be `RCTConvert+AirMap.m`
* Add the NPM module in the `dependencies` array in `.re-natal` as discussed above
* Run `npm install && re-natal deps`
* At one point I had to delete `node_modules/react-native-maps/.babelrc`, because the react packager couldn't transform the code properly, then I realized that wasn't the "correct" fix.
* The "correct way" is apparently this:

```
cd ./node_modules/react-native-maps
npm install --save-dev babel-plugin-module-resolver
```
* open .babelrc and change module-resolver to babel-plugin-module-resolver
* Drink


Install App on iOS Device
-------------------------

In XCode, navigate to the project's general settings tab:

* `open ios/earthquakes.xcodeproj/`
* Click on the blue project icon in the file explorer
* Select your "team" for code signing

![iosCodeSigning.png](iosCodeSigning.png)

* Press the Play button to run on your device/simulator

Install App on Android Device
-----------------------------

```
react-native run-android
```

Run
---

```
adb reverse tcp:8081 tcp:8081
adb reverse tcp:3449 tcp:3449
re-natal use-ios-device real
re-natal use-android-device real
re-natal use-figwheel
react-native start --nonPersistent & # Run react-native packager in the background
rlwrap lein figwheel ios android
```
