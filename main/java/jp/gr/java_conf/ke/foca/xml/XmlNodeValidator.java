package jp.gr.java_conf.ke.foca.xml;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.gr.java_conf.ke.foca.XmlConsistencyException;
import jp.gr.java_conf.ke.namespace.foca.Aspect;
import jp.gr.java_conf.ke.namespace.foca.BindDef;
import jp.gr.java_conf.ke.namespace.foca.Controller;
import jp.gr.java_conf.ke.namespace.foca.Converter;
import jp.gr.java_conf.ke.namespace.foca.DataFlow;
import jp.gr.java_conf.ke.namespace.foca.Gateway;
import jp.gr.java_conf.ke.namespace.foca.Injection;
import jp.gr.java_conf.ke.namespace.foca.Joinpoint;
import jp.gr.java_conf.ke.namespace.foca.LayerContext;
import jp.gr.java_conf.ke.namespace.foca.Logger;
import jp.gr.java_conf.ke.namespace.foca.Presenter;

/**
 * Created by YT on 2017/05/07.
 */

class XmlNodeValidator {

    public static LayerContext margeContext(LayerContext parent, LayerContext child) throws XmlConsistencyException {
        LayerContext ret = new LayerContext();

        // childの要素を全てコピー
        for (Logger e : child.getLogger()) ret.getLogger().add(e);
        for (Aspect e : child.getAspect()) ret.getAspect().add(e);
        for (DataFlow e : child.getDataFlow()) ret.getDataFlow().add(e);

        // childの要素の名前と重複しないものを追加する
        for (Logger p : parent.getLogger()) {
            boolean exist = false;
            for (Logger c : child.getLogger()) {
                if (p.getName().equals(c.getName())) {
                    exist = true;
                }
            }
            if (!exist) ret.getLogger().add(p);
        }
        for (Aspect p : parent.getAspect()) {
            boolean exist = false;
            for (Aspect c : child.getAspect()) {
                if (p.getName().equals(c.getName())) {
                    exist = true;
                }
            }
            if (!exist) ret.getAspect().add(p);
        }
        for (DataFlow p : parent.getDataFlow()) {
            boolean exist = false;
            for (DataFlow c : child.getDataFlow()) {
                if (p.getName().equals(c.getName())) {
                    exist = true;
                }
            }
            if (!exist) ret.getDataFlow().add(p);
        }

        // extends属性はchildのものをコピーする
        ret.setExtend(child.getExtend());
        return ret;
    }

    // DataFlow, Logger, Aspecter名の妥当性チェック
    public static void validateName(LayerContext context) throws XmlConsistencyException {

        Set<String> nameSet = new HashSet<String>();
        for (Logger logger : context.getLogger()) {
            String name = logger.getName();
            if (nameSet.contains(name)) throw new XmlConsistencyException("<Logger>要素: name属性が重複しています。");
        }
        nameSet.clear();
        for (Aspect aspect : context.getAspect()) {
            String name = aspect.getName();
            if (nameSet.contains(name)) throw new XmlConsistencyException("<Aspect>要素: name属性が重複しています。");
        }
        nameSet.clear();
        for (DataFlow dataflow : context.getDataFlow()) {
            String name = dataflow.getName();
            if (nameSet.contains(name)) throw new XmlConsistencyException("<DataFlow>要素: name属性が重複しています。");
        }
    }

    // XSD相当のチェックを実施する。ただし、正規表現のチェックは省略する。
    public static void validate(LayerContext context) throws XmlConsistencyException {

        // <DataFlow>は1個以上であること
        List<DataFlow> dfList = context.getDataFlow();
        if (dfList.isEmpty()) {
            throw new XmlConsistencyException("必須要素: <DataFlow>が存在しません。");
        }

        for (DataFlow df : dfList) {

            // DataFlowのname属性は必須
            if (df.getName() == null)
                throw new XmlConsistencyException("要素:<DataFlow>のname属性が存在しません。");

            // 子要素の整合性をチェック
            Controller ctrl = df.getController();
            Presenter prst = df.getPresenter();
            List<Gateway> gtwy = df.getGateway();

            // Gatewayの有無を基準にする
            if (gtwy.isEmpty()) {

                // Gateway無: Controller, Presenter必須
                if (ctrl == null || prst == null) {
                    throw new XmlConsistencyException(
                            "要素:<DataFlow>の子要素Gateway,Controller,Presenterが存在しません。");
                }
            } else {

                // Gateway有: Controller, Presenter不可
                if (ctrl != null || prst != null) {
                    throw new XmlConsistencyException(
                            "要素:<DataFlow>の子要素Gatewayが存在する場合、Controller,Presenterは定義できません。");
                }
            }

            // <DataFlow>の子要素は個別にチェック
            checkController(ctrl);
            checkPresenter(prst);
            checkGateway(gtwy);
            checkJoinpoint(df.getEntryPoint());
        }

        // <Aspect>はattributeにnullがないこと
        List<Aspect> aspectList = context.getAspect();
        if (!aspectList.isEmpty()) {
            for (Aspect aspect : aspectList) {
                String name = aspect.getName();
                String advice = aspect.getAdvice();
                if (name == null || advice == null) {
                    throw new XmlConsistencyException(
                            "要素: <Aspect>のattributeにnullが設定されています。name="
                                    + name + ", advice=" + advice);
                }
            }
        }

        // <Logger>がない場合はデフォルトロガーを追加
        List<Logger> loggerList = context.getLogger();
        if (loggerList.isEmpty()) {
            loggerList.add(new Logger());
        }
    }

    private static void checkController(Controller ctrl) throws XmlConsistencyException {
        if (ctrl == null) return;
        Joinpoint ip = ctrl.getInputPort();

        // <InputPort>必須
        if (ip == null) {
            throw new XmlConsistencyException("<Controller>の子要素:<InputPort>が存在しません。");
        }
        // Joinpointのチェック
        checkJoinpoint(ip);

        // Converterのチェック
        Converter cnv = ctrl.getConverter();
        if (cnv == null) {
            throw new XmlConsistencyException("<Controller>の子要素:<Converter>が存在しません。");
        }
        checkConverter(cnv);
    }

    private static void checkPresenter(Presenter prst) throws XmlConsistencyException {
        if (prst == null) return;

        Joinpoint view = prst.getView();

        // <InputPort>必須
        if (view == null) {
            throw new XmlConsistencyException("<Presenter>の子要素:<View>が存在しません。");
        }
        // Joinpointのチェック
        checkJoinpoint(view);

        // Converterのチェック
        Converter cnv = prst.getConverter();
        if (cnv == null) {
            throw new XmlConsistencyException("<Presenter>の子要素:<Converter>が存在しません。");
        }
        checkConverter(cnv);
    }

    private static void checkGateway(List<Gateway> gtwyList) throws XmlConsistencyException {
        if (gtwyList == null || gtwyList.isEmpty()) return;

        if (2 < gtwyList.size()) {
            throw new XmlConsistencyException(
                    "<DataFlow>に対して定義できる<Gateway>の最大要素数は2です。要素数=" + gtwyList.size());
        }

        for (Gateway gtwy : gtwyList) {
            Joinpoint dr = gtwy.getDriver();
            Joinpoint ip = gtwy.getInputPort();

            // <InputPort><Driver>どちらか必須
            if (dr == null && ip == null) {
                throw new XmlConsistencyException("<Gateway>の子要素:<InputPort>または<Driver>が存在しません。必ずどちらかを定義する必要があります。");
            }

            // Joinpointのチェック
            if (dr != null) checkJoinpoint(dr);
            if (ip != null) checkJoinpoint(ip);

            // Converterのチェック
            Converter cnv = gtwy.getConverter();
            if (cnv == null) {
                throw new XmlConsistencyException("<Gateway>の子要素:<Converter>が存在しません。");
            }
            checkConverter(cnv);
        }
    }

    private static void checkJoinpoint(Joinpoint jp) throws XmlConsistencyException {
        if (jp == null) return;
        Injection inj = jp.getInject();

        if (inj == null) {
            throw new XmlConsistencyException("必須要素:<Injection>が存在しません。");
        }

        String className = inj.getClazz();
        if (className == null || className.length() == 0) {
            throw new XmlConsistencyException("要素:<Injection>のclass属性が存在しません。");
        }
    }

    private static void checkConverter(Converter cnv) throws XmlConsistencyException {
        if (cnv == null) return;
        BindDef bindDef = cnv.getBindDef();
        Injection inj = cnv.getInject();

        // <BindDef><Injection>どちらか必須
        if (bindDef== null && inj == null) {
            throw new XmlConsistencyException("<Converter>の子要素:<InputPort>または<Driver>が存在しません。必ずどちらかを定義する必要があります。");
        }

        if (bindDef != null) {
            // outModel属性必須
            String outModel = bindDef.getOutModel();
            if (outModel == null) {
                throw new XmlConsistencyException("<Converter>の属性:outModelが存在しません。");
            }
        }
    }
}
