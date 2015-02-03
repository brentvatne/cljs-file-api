(ns cljs-file-api.core-test
  (:require-macros [cemerick.cljs.test :refer [is deftest testing done]]
                   [cljs.core.async.macros :refer [go go-loop]])
  (:require [cemerick.cljs.test :as t]
            [cljs.core.async :as async :refer [<! timeout]]
            [cljs-file-api.core :as file-api]))

(deftest test-set-csrf-token
  (testing "sets as default header"
    (file-api/add-csrf-token-to-default-headers! "abc")
    (is (= (deref file-api/default-headers) {"X-CSRF-TOKEN" "abc"}))))
