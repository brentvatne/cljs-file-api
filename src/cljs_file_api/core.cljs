(ns cljs-file-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<! >! put! chan]]
            [camel-snake-kebab.core :refer [->kebab-case-keyword ->snake_case_string]]
            [camel-snake-kebab.extras :refer [transform-keys]]))

(def default-headers (atom {}))

(defn xhr->clj [xhr]
  "Take a JSON response and convert it to :kebab-case-keyword"
  (transform-keys ->kebab-case-keyword (js->clj (.parse js/JSON (.-response xhr)))))

(defn set-default-header! [header-name header-value]
  (swap! default-headers assoc header-name header-value))

(defn add-csrf-token-to-default-headers! [token]
  (set-default-header! "X-CSRF-TOKEN" token))

(def file-api (.-FileAPI js/window))

(defn- upload* [options]
  (.upload file-api (clj->js options)))

(defn upload [file options]
  (let [channel (chan)
        default-options {:files {:file file}
                         :complete (fn [_ xhr _] (put! channel (xhr->clj xhr)))
                         :headers (merge @default-headers (:headers options))}
        merged-options (merge default-options (dissoc options :headers))]
    (upload* merged-options)
    channel))
