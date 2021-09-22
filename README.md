# Deprecation notice

With significant changes to recyclerview and stable jetpack compose being released, this project will no longer be maintained.

# Recycler Bubble
Provides fast scroll and section indexer for recycler view. Uses a different mechanism than similar libraries to provide smooth movement of scroller handle when scrolling the list. Can be used with vertical and horizontal RecyclerViews. You can style it or even use a custom layout and animations for your fast scroll handle and bubble.

![alt tag](http://i.imgur.com/Ugqhzud.gif)

## Usage

### Minimal working example

In your layout file:
```xml
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

In your `RecyclerView.Adapter`
```java
        public class MyAdapter ... implements SectionTitleProvider{
            
            ...
            
            @Override
            public String getSectionTitle(int position) {
                //this String will be shown in a bubble for specified position
                return getItem(position).substring(0, 1);
            }
            
        }
```

**Note**: You have to populate your adapter with enough items for the `FastScroll` to show. I has an auto-hide mechanism in case there is nothing to scroll.

### Horizontal orientation

You can use this library with horizontal LayoutManager. To do it use android:orientation="horizontal" attribute:

```xml
    <com.futuremind.recyclerviewfastscroll.FastScroller
        android:id="@+id/fastscroll"
        android:orientation="horizontal"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```

### Styling

Styling was introduced in version 0.1.5 with a limited set of styleable attributes. More are on their way.

You can style the attributes in the xml layout:

```xml
    <com.futuremind.recyclerviewfastscroll.FastScroller
           ...
           app:fastscroll__handleColor="#8f93d1"
           app:fastscroll__bubbleColor="#5e64ce"
           app:fastscroll__bubbleTextAppearance="@style/StyledScrollerTextAppearance"
           />
```

Or directly in the code:

```java
         fastScroller.setBubbleColor(0xffff0000);
         fastScroller.setHandleColor(0xffff0000);
         fastScroller.setBubbleTextAppearance(R.style.StyledScrollerTextAppearance);
```

See the example code for more info.

### Custom views

You can set custom layouts and animations for your handle and bubble, using:

```java
        myViewProvider = new MyScrollerViewProvider();
        fastScroller.setViewProvider(myViewProvider);
```

For more information, consult [`CustomScrollerViewProvider`](/example/src/main/java/com/futuremind/recyclerviewfastscroll/example/customview/CustomScrollerViewProvider.java) in the example code.

## Download

Download via Gradle:
```groovy
compile 'com.futuremind.recyclerfastscroll:fastscroll:0.2.5'
```

## License

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
