# Glory
Runtime permission requester, request dangerous permissions through Glory easily.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.glory:glory:1.0.2'
```

## How To Use

* Build Glory instance with builder.
```java
    Glory glory = Glory.builder()
        .context(ctx)
        .permissions(permissions)
        .build();
```

* Send a permission request.
```java
    glory.request(CallbackFactory.newCallback((response -> {
        // sth...
    })));
```

* If you want to show rationale you can enable on builder. Also Glory has own activity for requesting permission but you can change requester
through builder and add customized one.
```java
    final String[] permissions = {Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION};

    Glory.builder()
            .context(ctx)
            .requestCode(101) // optional
            .rationale("Message") // optional
            .permissions(permissions)
            .requester(customRequester) // optional
            .build()
            .request(CallbackFactory.newCallback((response -> {
                // sth...
            })));
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence

```
Copyright 2018 Nuri SEZGİN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
