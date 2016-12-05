package printControlComponents;

/**
 * Created by ycqfeng on 2016/12/4.
 */
public class PrintControlNode {
    //实例集合
    private InterfacePrintControlRegisterInstance instance;
    //打印信息
    private boolean isPrintErrorInformation;
    private boolean isPrintLogicInformation;
    private boolean isPrintDebugInformation;
    private boolean isPrintResultInformation;
    //构造函数
    public PrintControlNode(){
        this.isPrintErrorInformation = true;
        this.isPrintLogicInformation = false;
        this.isPrintDebugInformation = false;
        this.isPrintResultInformation = false;
    }
    //设置、获取实例
    public InterfacePrintControlRegisterInstance getInstances() {
        return instance;
    }

    public void setInstances(InterfacePrintControlRegisterInstance instance) {
        this.instance = instance;
    }
    //控制打印信息
    public boolean isPrintErrorInformation() {
        return isPrintErrorInformation;
    }

    public void setPrintErrorInformation(boolean printErrorInformation) {
        isPrintErrorInformation = printErrorInformation;
    }

    public boolean isPrintLogicInformation() {
        return isPrintLogicInformation;
    }

    public void setPrintLogicInformation(boolean printLogicInformation) {
        isPrintLogicInformation = printLogicInformation;
    }

    public boolean isPrintDebugInformation() {
        return isPrintDebugInformation;
    }

    public void setPrintDebugInformation(boolean printDebugInformation) {
        isPrintDebugInformation = printDebugInformation;
    }

    public boolean isPrintResultInformation() {
        return isPrintResultInformation;
    }

    public void setPrintResultInformation(boolean printResultInformation) {
        isPrintResultInformation = printResultInformation;
    }
}
