(ns earthquakes.ios.ui
  (:require [reagent.core :as r :refer [atom]]
            [earthquakes.common.ui :as common :refer [ReactNative]]))

; ios-specific React Components
(def tab-bar (r/adapt-react-class (.-TabBarIOS common/ReactNative)))
(def tab-bar-item (r/adapt-react-class (.-TabBarIOS.Item common/ReactNative)))

; Custom Components
