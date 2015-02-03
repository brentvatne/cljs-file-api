# cljs-file-api

A FileAPI wrapped that plays well with `core.async`

## Use it

First have FileAPI set up on your project already. No integration with
the closure compiler available at the moment. Include it on your page
with a script tag and then use this library as follows..

Add the following to your project dependencies:
```clojure
[ca.brentvatne/cljs-file-api "0.1.2"]
```

Then require it where necessary:

```clojure
(ns app.core
  (:require-macros [cljs.core.async.macros] :refer [go])
  (:require [cljs-file-api.core :as file-api]
            [cljs.core.async :as async))

  (go
    (let [response (async/<! (file-api/upload some-file "/endpoint-url/"))]
      (.log js/console response)
      (.alert js/window "done!")))
```
