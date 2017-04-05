 (ns ^:figwheel-no-load env.ios.main
  (:require [reagent.core :as r]
            [earthquakes.ios.core :as core]
            [figwheel.client :as figwheel :include-macros true]))

 (enable-console-print!)

(def cnt (r/atom 0))
(defn reloader [] @cnt [core/app-root])
(def root-el (r/as-element [reloader]))

(figwheel/watch-and-reload
 :websocket-url "ws://10.0.0.8:3449/figwheel-ws"
 :heads-up-display false
 :jsload-callback #(swap! cnt inc))

(core/init)