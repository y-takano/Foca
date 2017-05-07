/**
 * Converter API(Option) パッケージ.<br>
 * <br>
 * Converter APIを実装しています。<br>
 * InterfaceAdapterのメソッドが呼ばれた際に、出口メソッドのパラメータ型にデータ変換します。<br>
 * <br>
 * 　主なインターフェース：
 * <ul>
 *   <li> {@link jp.gr.java_conf.ke.foca.converter.ParamTypeConverter ParamTypeConverter}
 *   : データ変換対象のオブジェクトの変換処理を実装するためのインターフェース
 *   <li> {@link jp.gr.java_conf.ke.foca.converter.OutModelFactory OutModelFactory}
 *   : データ変換対象オブジェクトの生成を実装するためのインターフェース
 * </ul>
 * <br>
 * 【参考】Converterについて:<br>
 * 　　Converterの定義は、アプリケーションの都合に応じて以下2パターンから実装を選択します。<br>
 * <br>
 * 　　　・バインド定義による変換指定: 変換処理の全部、または一部をフレームワークの処理に任せる<br>
 * 　　　・カスタムアダプタの指定: 変換処理の全部をカスタムアダプタ(ユーザが作成するロジック)に任せる<br>
 * <br>
 * <pre>
 * 使用例（XML）：バインド定義の指定方法①: getter,setterによるデータ変換(定義のみ)
 *     {@literal <Controller>}
 *     {@literal     <InputPort ... />}
 *     {@literal     <Converter>}
 *     {@literal         <BindDef outModel="xxx.HogeDTO"/>}
 *     {@literal             <Bind>}
 *     {@literal                 <From getter="getData"/>}
 *     {@literal                 <To getter="setData"/>}
 *     {@literal             </Bind>}
 *     {@literal         </BindDef>}
 *     {@literal     </Converter>}
 *     {@literal </Controller>}
 * </pre>
 * <pre>
 * 使用例（XML）：バインド定義の指定方法②: カスタムのファクトリ、コンバータによるデータ変換(定義、ユーザ実装混合)
 *     {@literal <Controller>}
 *     {@literal     <InputPort ... />}
 *     {@literal     <Converter>}
 *     {@literal         <BindDef outModel="xxx.HogeDTO" factory="xxx.HogeDTOFactory"/>}
 *     {@literal             <Bind converter="xxx.HogeConverter"/>}
 *     {@literal         </BindDef>}
 *     {@literal     </Converter>}
 *     {@literal </Controller>}
 * </pre>
 * <pre>
 * 使用例（XML）：カスタムアダプタの指定方法(ユーザ実装のみ)
 *     {@literal <Controller>}
 *     {@literal     <InputPort ... />}
 *     {@literal     <Converter>}
 *     {@literal         <Inject class="xxx.CuntomHogeAdapter"/>}
 *     {@literal     </Converter>}
 *     {@literal </Controller>}
 * </pre>
 *
 * @see jp.gr.java_conf.ke.foca.converter.ParamTypeConverter
 * @see jp.gr.java_conf.ke.foca.converter.OutModelFactory
 * @see jp.gr.java_conf.ke.foca.annotation.entrance.InterfaceAdapter
 */
package jp.gr.java_conf.ke.foca.converter;
