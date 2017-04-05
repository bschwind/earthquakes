(ns earthquakes.ios.ui
  (:require [reagent.core :as r :refer [atom]]
            [earthquakes.common.ui :as shit :refer [ReactNative]]))

; ios-specific React Components
(def tab-bar (r/adapt-react-class (.-TabBarIOS shit/ReactNative)))
(def tab-bar-item (r/adapt-react-class (.-TabBarIOS.Item shit/ReactNative)))

; Custom Components
