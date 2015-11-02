GWSBindroid
============

Soft fork of bindroid until Google DataBinding comes with the stable android gradle plugin.

Usage
=====

I use jitpack to upload my libraries so you put this in your root buildscript:

```groovy
allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }
```
Than in the module buildscript:


```groovy
compile 'com.github.shareme:GWSBindroid:{latest-release-number}@aar'
```


A simple ViewModel might look like this:

```java
public class FooModel {
  // An int property named "Bar"
  private TrackableInt bar = new TrackableInt(0);
  public int getBar() {
    return bar.get();
  }
  public void setBar(int value) {
    bar.set(value);
  }

  // A String property named "Baz"
  private TrackableField<String> baz = new TrackableField<String>();
  public String getBaz() {
    return baz.get();
  }
  public void setBaz(String value) {
    baz.set(value);
  }
}
```
In your `Activity`'s `OnCreate()` override or `View`'s constructor, call `bind()` for each pair of properties you wish to track each other:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  ViewModel model = new ViewModel();

  UiBinder.bind(new EditTextTextProperty((EditText) this.findViewById(R.id.TextField)), model,
      "StringValue", BindingMode.TWO_WAY);
  UiBinder.bind(this, R.id.TextView, "Text", model, "StringValue", BindingMode.ONE_WAY);
}
```
Sometimes, the values on your model will not map directly to the types of values that the views in your UI expect.  For example, you may have an `int` property on your model that will be displayed as a `String` in a `TextView`.  Bindroid solves this problem using converters, which can be passed to a `Binding` or to `UiBinder.bind()`:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);

  ViewModel model = new ViewModel();

  UiBinder.bind(new EditTextTextProperty((EditText) this.findViewById(R.id.TextField)), model,
      "StringValue", BindingMode.TWO_WAY);
  UiBinder.bind(this, R.id.TextView, "Text", model, "StringValue", BindingMode.ONE_WAY);
  UiBinder.bind(this, R.id.ListView, "Adapter", model, "Dates", BindingMode.ONE_WAY,
      new AdapterConverter(DateView.class));

  UiBinder.bind(this, R.id.CountTextView, "Text", model, "Count", BindingMode.ONE_WAY,
      new ToStringConverter("Count: %d"));
  UiBinder.bind(this, R.id.TextLengthView, "Text", model, "TextLength", BindingMode.ONE_WAY,
      new ToStringConverter("Text length: %d"));
  UiBinder.bind(this, R.id.SumView, "Text", model, "CountPlusTextLength", BindingMode.ONE_WAY,
      new ToStringConverter("Sum: %d"));
  UiBinder.bind(this, R.id.EvenSpinner, "Visibility", model, "CountIsEven", BindingMode.ONE_WAY,
      new BoolConverter());
  UiBinder.bind(this, R.id.OddSpinner, "Visibility", model, "CountIsEven", BindingMode.ONE_WAY,
      new BoolConverter(true));
}
```
You may have existing classes that you want to expose as trackable objects that can be bound to using Bindroid.  This process requires a little instrumentation, but is relatively straightforward.  To set up your objects for trackability, you will use the `Trackable` class.  Any time an accessor to your object is called, you must call `trackable.track()`.  Whenever the value of the thing being accessed changes, you must call `trackable.notifyTrackers()`.  Once you've done that, these values can easily be bound using Bindroid.

For example, let's suppose we have an existing model object (`Foo`) using a legacy change notification mechanism.  Your ViewModel that wraps this model would then look like this:

```java
public class FooViewModel {
  private FooModel model;
  private Trackable trackable = new Trackable();
  public FooViewModel(FooModel model) {
    this.model = model;
    model.addChangeListener(new ChangeListener() {
      public void propertyChanged() {
        trackable.notifyTrackers();
      }
    });
  }

  // Each wrapped property would need to call track()
  public String getBar() {
    trackable.track();
    return model.getBar();
  }
  public void setBar(String value) {
    model.setBar(value);
  }

  // Alternatively, you can directly expose the model, but call track()
  // on the way in so that any listeners will know to listen for change
  // notifications
  public FooModel getFoo() {
    trackable.track();
    return model;
  }
}
```



Target Android API Range
========================

Api 16 to api 23.

Credits
========

[ Bindroid]()

Fred Grott(aka shareme  GrottWorkShop)
[MyGithubProfile](https://github.com/shareme/MyGithubProfile)

Former JavaME and JavaEE developer that made the transition to Android Native java Application Development.
Multi-computer-language polyglot that can jump into anything and I do not play follow-the-leader but
often follow my own unique way.(No recruiters, please for any reason)

[Github profile](https://github.com/shareme)
[Bitbucket profile](https://bitbucket.org/fredgrott)
[G+ profile](https://plus.google.com/u/0/+FredGrott/about)
[Twitter profile](https://twitter.com/fredgrott)
[Facebook profile](http://www.facebook.com/fredgrott)
[DeviantArt profile](http://shareme.deviantart.com)
[BeHance profile](https://www.behance.net/gwsfredgrott)
[Dribbble profile](https://dribbble.com/FredGrott)
[AngelList profile](https://angel.co/fred-grott)
[BuiltINChicago profile](http://www.builtinchicago.org/member/fred-grott)
[HackerNews profile](https://news.ycombinator.com/user?id=fredgrott)
[Geeklist profile](https://geekli.st/fredgrott)
[Medium profile](https://medium.com/@fredgrott)
[StackOverflow profile](http://stackoverflow.com/users/237740/fred-grott)
[Blogger blog](http://grottworkshop.blogspot.com)
[Reddit profile](http://www.reddit.com./user/fredgrott/)
[Quora profile](http://www.quora.com/Fred-Grott)
[YouTube channel](https://www.youtube.com/c/FredGrott?gvnc=1)
[AboutMe profile](https://about.me/fredgrott)
[LinkedIN profile](http://www.linkedin.com/in/shareme/en)
[Xing profile](https://www.xing.com/profile/Fred_Grott?sc_o=mxb_p)
[SlideShare profile](http://www.slideshare.net/shareme)
[SpeakerDeck profile](https://speakerdeck.com/fredgrott)
[Android Hacker Tumbler](https://www.tumblr.com/blog/androidhacker)
[Ustream](https://www.ustream.tv/manage-show/12940149)
[AboutMe](https://about.me/fredgrott)

License
=======

[Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.txt)

Resources
=========

[Google Android Developer Site](http://developer.android.com)

[Google Android Developer Tool Site](http://tools.android.com)

[Google Android Developer Blog](http://android-developers.blogspot.com/)


[StackOverflow Android Questions](http://stackoverflow.com/questions/tagged/android)

[Gradle](http://gradle.org)

[Reddit-androidev](http://reddit.com/r/androdev/)

[AndroidChat at Slack](https://androidchat.slack.com/messages/development/)

[Amazon Android Dev Site](https://developer.amazon.com/public)

[JavaRanch Android Forum](http://www.coderanch.com/forums/f-93/Android)

[Android Development Tools G+ community](https://plus.google.com/communities/114791428968349268860)

[Android Development G+ Community](https://plus.google.com/communities/105153134372062985968)

[Android Developers G+ Community](https://plus.google.com/+AndroidDevelopers/posts)

[Android Design G+ Community](https://plus.google.com/communities/113499773637471211070)

[UX Design for Developers](https://plus.google.com/communities/103651070366324568638)

[Android MVP G+ Community](https://plus.google.com/communities/114285790907815804707)
