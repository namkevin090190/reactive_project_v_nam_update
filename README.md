# app_automation


## I. Hướng dẫn setup môi trường test với appium cho Android

#### Bước 1: Cài đặt [homebrew:](https://brew.sh/)
- Mở Terminal, nhập vào:

```bash
$ /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```


#### Bước 2: Cài đặt java JDK:
- Download và cài đặt: [Java SE Development Kit 8](https://www.oracle.com/technetwork/java/javaee/downloads/jdk8-downloads-2133151.html) (_Khuyến khích_)
- Hoặc thông qua Terminal:

```bash
$ brew install java --cask 
$ java -version
``` 

- Nếu cài đặt thành công sẽ có kết quả giống vầy:
```
java version "1.8.0_211"
Java(TM) SE Runtime Environment (build 1.8.0_211-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.211-b12, mixed mode)
```

#### Bước 3: Cài đặt MAVEN
- Mở Terminal, nhập vào:

```bash
$ brew install maven 
```


#### Bước 4: Download [IntelliJ IDEA:](https://www.jetbrains.com/idea/download/#section=mac) (`Chọn phiên bản Community Edition, free và đủ dùng`)

#### Bước 5: Download [Android studio](https://developer.android.com/studio)
- Cài đặt và khởi động Android studio
- Mở Android `SDK Manager`
- Download SDK Platforms (Android 6,7,8,9,..), SDK Tools

<img src="https://user-images.githubusercontent.com/76426965/118747830-f2955980-b884-11eb-861a-b68bee8c15e5.png" alt="Example" width="720" height ="478"/>

#### Bước 6: Cài đặt [nodeJS](https://nodejs.org/en/download/)

- Tải tại trang: [https://nodejs.org/en/download/](https://nodejs.org/en/download/) sau đó cài đặt.

#### Bước 7: Cài đặt npm
- Mở Terminal, nhập vào:

```bash
$ npm install npm -g
```

#### Bước 8: Setup môi trường.

>> Nếu như máy tính chưa có có file .bash_profile, thì mình tạo file .bash_profile trước:

- Mở Terminal, nhập vào:

```bash
$ cd ~/
$ touch .bash_profile
```

- Khi tạo `.bash_profile` rồi, mở Terminal, nhập vào:
```bash
$ nano ~/.bash_profile
hoặc
$ open -e .bash_profile
```

- Nhập các dòng sau vào .bash_profile, sau đó lưu lại:

```
export ANDROID_HOME=/Users/[]/Library/Android/sdk
export PATH=$ANDROID_HOME/platform-tools:$PATH
export PATH=$ANDROID_HOME/tools:$PATH
export JAVA_HOME=$(/Library/Java/JavaVirtualMachines/jdk1.8.0_141.jdk)
export PATH=${PATH}:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools
export PATH=${JAVA_HOME}/Contents/Home/bin:$PATH
export PATH=/usr/local/bin:/usr/local/sbin:~/bin:$PATH
export PATH=$PATH:/opt/bin:$PATH
```

>> Lưu ý:
thay thế *[]* bằng tên user trên máy của bạn.
thay thế *jdk1.8.0_141.jdk* bằng bản jdk đã cài đặt


#### Bước 9: Cài đặt Appium
- Mở Terminal, nhập vào:

```bash
# Chọn 1 trong 2 bản bên dưới, với SBH cần dùng bản 2.x để thay đổi snapshotMaxDepth
# Install appium published (current 1.x.x)
$ npm install -g appium
$ npm install wd
# Install appium 2.x
$ npm install -g appium@next
$ npm install wd
```
Sau đó tiếp tục tải và cài đặt [Appium Desktop](https://github.com/appium/appium-desktop/releases)

- Kiểm tra appium đã cài đặt thành công hay chưa:
```bash
$ appium -v
```
- Nếu trả về version của appium (ví dụ: `1.21.0`) là đã cài đặt thành công.
- Nếu chọn cài appium 2.x thì cần cài thêm driver cho IOS và Android

```bash
$ appium driver install uiautomator2
$ appium driver install xcuitest
```

- Để kiểm tra các driver appium đang cài
```bash
$ appium driver list
```

- Để mở appium bằng terminal:
```bash
$ appium
```

- Nếu mọi thứ suôn sẽ thì kết quả sẽ giống vầy.
```
[Appium] Welcome to Appium v1.21.0
[Appium] Appium REST http interface listener started on 0.0.0.0:4723
```

#### Bước 10: Setup adb:
- Mở Terminal, nhập vào:
```bash
$ brew install android-platform-tools --cask
$ adb devices
```

#### Bonus: connect real device via Wifi
```bash
$ adb usb
$ adb tcpip 5555
$ adb connect device_ip_address
```
Note:
- device & pc phải cùng wifi
- Nếu ko tạo được connection thì
    - On device: Wireless debugging > Pair device with pairing code
    - On Terminal: adb pair ip:port pairing_code

----------------------------------------------------------------------------------------
## II. Hướng dẫn setup môi trường test với appium cho IOS

#### Bước 1: Tải và cài đặt [Xcode](https://itunes.apple.com/vn/app/xcode/id497799835?mt=12) phiên bản mới nhất.
>> Xcode khá nặng, nên ưu tiên tải trước, sau đó thực hiện các bước tiếp theo. Khi cài đặt xong Xcode, tiến hành cài đặt Xcode command line.

##### Cài đặt Xcode Command line (Thực hiện sau khi đã cài đặt xong Xcode)
- Mở Terminal, nhập vào:

```bash
$ xcode-select --install
```  
#### Bước 2: Cài đặt [homebrew:](https://brew.sh/)
- Mở Terminal, nhập vào:

```bash
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

#### Bước 3: Cài đặt java JDK:
- Như Android.

#### Bước 4: Download [IntelliJ IDEA:](https://www.jetbrains.com/idea/download/#section=mac) (`Chọn phiên bản Community Edition, free và đủ dùng`)


#### Bước 5: Cài đặt [nodeJS](https://nodejs.org/en/download/)
- Như Android.

#### Bước 6: Cài đặt npm
- Như Android.

#### Bước 7: Setup môi trường.
- Như Android.

#### Bước 8: Cài đặt Carthage
- Mở Terminal, nhập vào:

```bash
$ brew install carthage
$ npm i -g webpack
```

#### Bước 9: Cài đặt Appium
- Như Android.

#### Bước 10: Cài đặt Appium Doctor
- Mở Terminal, nhập vào:

```bash
$ npm install -g appium-doctor 
```

#### Bước 11 : Setup để chạy được trên real device  (Lúc này chắc chắn Xcode và Xcode Command line đã cài xong rồi nha, nếu chưa cài thì chờ cài xong rồi làm tiếp)
- Mở Terminal, nhập vào:

```bash
$ brew install libimobiledevice --HEAD
$ brew install ideviceinstaller
$ npm install -g ios-deploy
$ gem install xcpretty
```

#### Bước 12: nếu đã xong bước 11 nhưng vẫn không thể run dc trên real device thì thực hiện các bước sau:
- Với bản Desktop:
    + Open folder: /Applications/Appium.app/Contents/Resources/app/node_modules/appium/node_modules/appium-webdriveragent
        * Run
       ```
           $ mkdir -p Resources/WebDriverAgent.bundle
         $ ./Scripts/bootstrap.sh -d 
       ```

    + Run WebDriverAgent.xcodeproj by Xcode
        * Chọn Automatically manage signing cho cả WebDriverAgentLib và WebDriverAgentRunner ở tab General.
        * Chọn Development Team. (Chưa có tài khoản thì tạo tự tạo nha)
        * Chọn WebDriverAgentRunner, chọn tab Build Settings. Đổi value của Product Bundle Identifier từ com.facebook.WebDriverAgentRunner thành 1 cái gì khác bất kỳ
    + Build thử trên Simulator/Real device tương ứng (follow các hình bên dưới)

<img src="https://user-images.githubusercontent.com/76426965/118749676-3a69b000-b888-11eb-9549-5d744883ee68.png" alt="Example" width="720" height ="478"/>
<img src="https://user-images.githubusercontent.com/76426965/118749686-3e95cd80-b888-11eb-87d4-4d6713774a8b.png" width="720" height ="478"/>
<img src="https://user-images.githubusercontent.com/76426965/118749689-405f9100-b888-11eb-85ab-ef177aafe837.png" width="720" height ="478"/>
<img src="https://user-images.githubusercontent.com/76426965/118749690-4190be00-b888-11eb-976a-fb449c64bf69.png" width="720" height ="478"/>

- Với bản terminal: làm tương tự bản Desktop, thay link Folder: /usr/local/lib/node_modules/appium/node_modules/appium-webdriveragent
-------------------------------------------------------------------------------------
## III. Một số lưu ý:
- iOS: Để lấy UDID, Mở Xcode > Window > Devices and Simulators, copy dòng identifier. (hoặc gõ lệnh instruments -s devices (Xcode 13: xcrun xctrace list devices) trên terminal)
- Android: khi thiếu chromedriver thì run
  ```appium --allow-insecure chromedriver_autodownload```
  thay vì
  ```appium```
- MobileWeb: phần setup screenshot cần thêm ```capabilities.setCapability("nativeWebScreenshot",true);```

**Cách lấy bundle ID:**
|Steps|img/description|
|---|---|
|1. Search app trên google và mở lên|<img src="https://user-images.githubusercontent.com/76426965/140476668-996d1528-e220-4c72-8479-3d0eb6371d09.png" align="left" width="650">|
|2. Nhập id tìm thấy vào link như hình để down file về|<img src="https://user-images.githubusercontent.com/76426965/140476684-f605e307-1479-4a75-aa18-357e9346755a.png" align="left" width="650">|
|3. Mở file vừa down và search bundleID|<img src="https://user-images.githubusercontent.com/76426965/140476689-2af43905-6e65-40f1-ad9e-df6f21695628.png" align="left" width="650">|
