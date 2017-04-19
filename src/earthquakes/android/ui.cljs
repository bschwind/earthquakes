(ns earthquakes.android.ui
  (:require [reagent.core :as r :refer [atom]]))

(def BottomNavigation (js/require "react-native-material-bottom-navigation"))

; React Components

; Third-party Components
(def bottom-nav (r/adapt-react-class (.-default BottomNavigation))) ;; Default is for ES6 exported classes
(def bottom-tab (r/adapt-react-class (.-Tab BottomNavigation)))

; Custom Components
