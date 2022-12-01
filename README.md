# Custom Material EditText [![](https://jitpack.io/v/arifz-xyz/material-edittext.svg)](https://github.com/arifz-xyz/material-edittext)

An EditText inspired by Material Text Fields and packed with useful functionalities 

<p>
    <img src="screenshots/material_edit_text.gif" width="35%"/>
</p>

## Dependency Add
Minimum required SDK 21

``` Gradle

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
	implementation 'com.github.arifz-xyz:material-edittext:0.0.5-alpha04'
}

```
## How to use

### XML
```
<xyz.arifz.materialedittext.MaterialEditText
   android:id="@+id/met"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:layout_margin="16dp"
   app:hint="This is custom edittext"
   app:isHintFloating="true"
   app:isEnabled="true"
   app:isRequired="true"
   app:inputType="address"
   app:maxLines="3"
   app:layout_constraintBottom_toBottomOf="parent"
   app:layout_constraintEnd_toEndOf="parent"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toTopOf="parent" />
   
```

## Attributes

* **isRequired**, takes boolean value. Shows required asterisk symble.
* **isEnabled**, takes boolean value. Enable or Disable user input.
* **hint**, hint text. also acts as lebel if isHintFloating=true.
* **isHintFloating**, makes hint text as floating lebel like MaterialEditText Label. Default value "true".
* **maxLines**,  takes int value. Enables multiline input.
* **radius**,  box stroke radious of input field.
* **isReadOnly**, disables input and behaves like a textfield.
* **inputType**, available enums are - digit, digit_fractional, name, address, email, phone and password.

## Thanks

 * Special Thanks to [Md Arif](https://github.com/arifbd), [Shakibuzzaman](https://github.com/Shakibuzzaman3104) and [Tanvir](https://github.com/tanvir-media365) for their contribution.
