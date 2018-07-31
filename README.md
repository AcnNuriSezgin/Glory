# Glory
Runtime permission requester, can request dangerous permissions through Glory.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.android.glory:glory:1.0.0'
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
    glory.request(new CallbackFactory.Callback() {
            @Override
            public void onPermissionResult(Response response) {
                // sth...
            }
        });
```

* If you want show rationale you can enable on builder. Also Glory has own activity for requesting permission but you can change requester
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
            .request(new CallbackFactory.Callback() {
                @Override
                public void onPermissionResult(Response response) {

                }
            });
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence
Copyright 2018 Nuri SEZGIN

Apache License 2.0