# Foca - the Framework Of Clean Architecture. 
クリーンアーキテクチャの思想を落とし込んだ軽量DIコンテナ.あらゆるアプリケーションに適用可能.  
  
使用可能言語: java または android
  
The Clean Architecture原文: https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html  
The Clean Architecture邦訳: http://blog.tai2.net/the_clean_architecture.html  
  
＜メリット＞:  
　クリーンアーキテクチャの恩恵を受けること.(MVCアーキテクチャ, ドメイン駆動, テスト駆動等にも適合する。)        
　システムやアプリケーションの機能独立性が高まり、保守性に期待できる. チーム開発や標準化に適している.    
  
＜デメリット＞:  
　厳密に機能分割することが求められるため、クラス数が一定以上必要になること.  
　ソースコードのサイズを最小化したい場合には適さない. また、リフレクションを多用するため、性能を突き詰めるような開発には適さない.      
  
＜使用上の注意＞:  
　本フレームワークは純粋なクリーンアーキテクチャから発想を得て個人的な解釈を加えたものとなっている可能性があります.  
　本開発に際し、全てy-takano個人で行っており提唱元のRobert Martin氏をはじめとする関係団体とは何ら関わりがありません.  
　また、団体や法人の公式製品でもありません.以上の関係で何らかのトラブルが発生した場合、予告なく公開を停止する可能性があります.  
  
-------------

## Overview（概要）
FocaはDI/AOPを実現することで、アプリケーション層と外部構造を疎結合させるための手助けをします.  
<dl>
  <dt>・Injection API(Core):</dt>
  <dd>DIコンテナ設定ファイル(XML), またはXML構造のBeanを利用して依存性を表現します。</dd>
  <dt>・Annotation API(Core):</dt>
  <dd>依存性の注入をする箇所をアノテーションで実装するための仕組み。</dd>
  <dt>・Converter API(Option):</dt>
  <dd>レイヤ間の通信データ変換を簡潔にするための仕組み。</dd>
  <dt>・Aspecter API(Option):</dt>
  <dd>所定のjoinpointに対してadviceを実装するための仕組み。</dd>
</dl>

※ 凡例: Core-主要機能:必須, Option-オプション機能:任意  

| API | パッケージ名 | パッケージ説明 |
|:-----------:|:------------|:------------|
| Injection API(Core) | [jp.gr.java_conf.ke.foca](https://github.com/y-takano/Foca/tree/master/main/java/jp/gr/java_conf/ke/foca), [jp.gr.java_conf.ke.namespace.foca](https://github.com/y-takano/Foca/tree/master/main/java/jp/gr/java_conf/ke/namespace/foca) | DIコンテナの設定を行うFocaクラスとXSDから生成されたBeanクラス、FW例外クラスを保有します。 |
| Annotation API(Core) | [jp.gr.java_conf.ke.foca.annotation](https://github.com/y-takano/Foca/tree/master/main/java/jp/gr/java_conf/ke/foca/annotation),  | 依存性の注入箇所を指定するための機能を保有します。  |
| Converter API(Option) | [jp.gr.java_conf.ke.foca.converter](https://github.com/y-takano/Foca/tree/master/main/java/jp/gr/java_conf/ke/foca/converter) | データ変換時に利用するインターフェースおよびデフォルト実装を保有します。 |
| Aspecter API(Option) | [jp.gr.java_conf.ke.foca.aop](https://github.com/y-takano/Foca/tree/master/main/java/jp/gr/java_conf/ke/foca/aop) | adviceの抽象クラス、およびDefaultLoger・TraceLogAdviceの実装を保有します。 |

-------------

## Install（環境設定）

FocaはgithubによるMavenリポジトリの公開をしています.  
jar形式とaar形式を別々のartifactとして公開していますので、用途に応じてご自由に選択してください.  
  
＜Mavenリポジトリ情報＞  
リポジトリURL: http://y-takano.github.com/Foca/repository  
groupId: y-takano  
artifactId(aar): foca-aar  
artifactId(jar): foca-jar  
最新version: 1.0.0  
  
Android Studioから利用する場合は、以下の設定でライブラリが適用されます.  

##### build.gradle

```gradle
repositories {
    maven { url 'http://y-takano.github.com/Foca/repository' }
}
dependencies {
    compile 'y-takano:foca-aar:1.0.0'
}
```

-------------

## Requirement（依存関係）

依存ライブラリなし。動作保障環境は以下の通り。

>　Java Runtime: 1.5(Tiger)以上  
>　Android minSdkLevel: 8(Froyo)以上 （最低動作保証APIレベル）  

-------------

## Usage（使い方）

XSD: https://github.com/y-takano/Foca/blob/master/foca-dicon.xsd  
XMLサンプル: https://github.com/y-takano/Foca/blob/master/foca-dicon_sample.xml  

### Javadoc
javadoc https://y-takano.github.io/Foca/site/apidoc/index.html

以下、主要機能のサンプルコードを提示します。

### Injection API(Core)

###### DIコンテナ設定ファイル(XML)

- DataFlowの設定(必須)
```xml:foca-dicon.xml
<LayerContext>
  <DataFlow type="UI" name="test">
    <Controller>
       <InputPort>...</InputPort>
       <Converter>...</Converter>
    </Controller>
    <Presenter>
       <View>...</View>
       <Converter>...</Converter>
    </Presenter>
  </DataFlow>
</LayerContext>
```

- Loggerの設定(任意)
```xml:foca-dicon.xml
<LayerContext>
  <!-- 定義しない場合はDEBUGレベルでデフォルトロガーが適用される。 -->
  <Logger level="DEBUG" name="sample" class="xxx.xxx.xxx.XXXLogger"/>
</LayerContext>
```

- Aspecterの設定(任意)
```xml:foca-dicon.xml
<LayerContext>
  <!-- トレースログのadviceは標準実装されているため、この定義で有効化可能。 -->
  <Aspect name="tracelog" advice="jp.gr.java_conf.ke.foca.aop.TraceLogAdvice"/>
</LayerContext>
```

###### APIの使い方
```java
import jp.gr.java_conf.ke.foca.Foca

// サンプルXMLファイルをメモリに展開する
Foca.updateDefault(new URL("https://raw.githubusercontent.com/y-takano/Foca/master/foca-dicon_sample.xml");

// DIを実行
SampleObject obj = Foca.getDefault().createInstance(SampleObject.class);
```
-------------

### Annotation API(Core)

##### Dto.java
```java
class Dto {
    private String str;
    public void set(String str) { this.str = str; }
    public String get() { return str; }
    public String toString() { return get(); }
}
```

##### Main.java
```java
import jp.gr.java_conf.ke.foca.annotation.Controller;
import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;

class Main {

    @Controller(name="test")
    private InterfaceAdapter<Dto, RuntimeException> controller;

    @Log
    private Logger logger;

    public void execute() {
        Dto dto = new Dto();
        dto.set("Hello World");
        logger.debug("before usecase");
        controller.invoke(dto);
        logger.debug("after usecase");
    }
}
```

##### Usecase.java
```java
import jp.gr.java_conf.ke.foca.annotation.Presenter;
import jp.gr.java_conf.ke.foca.annotation.InputPort;
import jp.gr.java_conf.ke.foca.annotation.Log;
import jp.gr.java_conf.ke.foca.adapter.InterfaceAdapter;

class Usecase {

    @Presenter(name="test")
    private InterfaceAdapter<Dto, RuntimeException> presenter;

    @Log
    private Logger logger;

    @InputPort
    public void doSomothing(Dto dto) {
        dto.set(dto.get() + "!!!");
        logger.debug("before something");
        presenter.invoke(dto);
        logger.debug("after something");        
    }
}
```

##### View.java
```java
import jp.gr.java_conf.ke.foca.annotation.View;

class View {

    @View
    public void display(Dto dto) {
        System.out.println(dto);
    }
}
```
##### System.out
> 20XX/XX/XX XX:XX:XX:XXX JST: [DEBUG]: before usecase  
> 20XX/XX/XX XX:XX:XX:XXX JST: [DEBUG]: before something  
> Hello World!!!  
> 20XX/XX/XX XX:XX:XX:XXX JST: [DEBUG]: after something  
> 20XX/XX/XX XX:XX:XX:XXX JST: [DEBUG]: after usecase  

-------------

### Unittest
Unittest: https://github.com/y-takano/Foca/blob/master/test/java/jp/gr/java_conf/ke/foca/ExampleUnitTest.java  

上記のユニットテストを動作させると、以下の標準出力が得られます。
##### System.out
> 2017/05/06 09:04:55:225 JST: [INFO ]: test start.  
> 2017/05/06 09:04:55:314 JST: [DEBUG]: start download dicon-xml. from https://raw.githubusercontent.com/y-takano/Foca/master/foca-dicon_sample.xml  
> 2017/05/06 09:05:09:114 JST: [DEBUG]: finish xml parse and update di-container.  
> 2017/05/06 09:05:09:114 JST: [INFO ]: DefaultLogger Level:DEBUG  
> 2017/05/06 09:05:09:114 JST: [DEBUG]: before inject.  
> 2017/05/06 09:05:10:638 JST: [TRACE]: call  : jp.gr.java_conf.ke.foca.ExampleUnitTest.TestDBGatewayStub#entrypoint(), param=null  
> 2017/05/06 09:05:10:638 JST: [DEBUG]: [ENTRYPOINT]: called.  
> 2017/05/06 09:05:10:639 JST: [TRACE]: return: jp.gr.java_conf.ke.foca.ExampleUnitTest.TestDBGatewayStub#entrypoint(), return=(void) null  
> 2017/05/06 09:05:10:640 JST: [DEBUG]: after inject.  
> 2017/05/06 09:05:10:640 JST: [DEBUG]: [MAIN   ]: start repaint.  
> 2017/05/06 09:05:10:651 JST: [TRACE]: call  : jp.gr.java_conf.ke.foca.ExampleUnitTest.TestUsecase#update(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data), param=Hello   
> 2017/05/06 09:05:10:651 JST: [DEBUG]: [USECASE]: select database.  
> 2017/05/06 09:05:10:652 JST: [TRACE]: call  : jp.gr.java_conf.ke.foca.ExampleUnitTest.TestDBGatewayStub#select(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data), param=Hello Foca  
> 2017/05/06 09:05:10:652 JST: [DEBUG]: [GATEWAY]: select result: Hello Foca  
> 2017/05/06 09:05:10:652 JST: [TRACE]: return: jp.gr.java_conf.ke.foca.ExampleUnitTest.TestDBGatewayStub#select(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data), return=(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data) Hello Foca  
> 2017/05/06 09:05:10:652 JST: [DEBUG]: [USECASE]: update display.  
> 2017/05/06 09:05:10:652 JST: [TRACE]: call  : jp.gr.java_conf.ke.foca.ExampleUnitTest.TestView#print(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data), param=Hello Foca  
> 2017/05/06 09:05:10:653 JST: [INFO ]: [VIEW   ]: Hello Foca World!  
> 2017/05/06 09:05:10:653 JST: [TRACE]: return: jp.gr.java_conf.ke.foca.ExampleUnitTest.TestView#print(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data), return=(void) null  
> 2017/05/06 09:05:10:653 JST: [DEBUG]: [USECASE]: complete.  
> 2017/05/06 09:05:10:653 JST: [TRACE]: return: jp.gr.java_conf.ke.foca.ExampleUnitTest.TestUsecase#update(jp.gr.java_conf.ke.foca.ExampleUnitTest.Data), return=(void) null  
> 2017/05/06 09:05:10:654 JST: [DEBUG]: [MAIN   ]: end repaint.  
> 2017/05/06 09:05:10:657 JST: [INFO ]: test end.  

-------------

## Licence
[MIT](http://opensource.org/licenses/mit-license.php)
