# recycler-fast-scroll
Provides fast scroll and section indexer for recycler view. Uses a different mechanism than similar libraries to provide smooth movement of scroller handle when scrolling the list. Can be used with vertical and horizontal RecyclerViews.

This library is still in early development stage. PRs are welcome.

## Usage
--------
In your layout file:
```java
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        />

    <com.futuremind.recyclerviewfastscroll.FastScroller
        android:id="@+id/fastscroll"
        android:orientation="vertical"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_alignParentRight="true"/>

</RelativeLayout>
```

In Activity/Fragment:

```java
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        //has to be called AFTER RecyclerView.setAdapter()
        fastScroller.setRecyclerView(recyclerView);
```

### Horizontal orientation

You can use this library with horizontal LayoutManager. To do it use android:orientation="horizontal" attribute:

```java

    <com.futuremind.recyclerviewfastscroll.FastScroller
        android:id="@+id/fastscroll"
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

```

## Download
--------

Download via Gradle:
```groovy
compile 'com.futuremind.recyclerfastscroll:fastscroll:0.1.2'
```

## License
-------

    Copyright 2015 Future Mind

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
