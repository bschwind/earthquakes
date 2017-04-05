adb reverse tcp:8081 tcp:8081
adb reverse tcp:3449 tcp:3449
re-natal use-ios-device real
re-natal use-android-device real
re-natal use-figwheel
react-native start --nonPersistent & # Run react-native packager in the background
rlwrap lein figwheel ios android
