package printControlComponents;

/**
 * Created by ycqfeng on 2016/12/4.
 */
public class PrintControl {
    PrintControlNode[] nodes;

    //设置节点输出属性
    public void setPrintResultInfoState(InterfacePrintControlRegisterInstance instance, boolean state){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return;
        }
        node.setPrintResultInformation(state);
    }
    public void setPrintLogicInfoState(InterfacePrintControlRegisterInstance instance, boolean state){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return;
        }
        node.setPrintLogicInformation(state);
    }
    public void setPrintDebugInfoState(InterfacePrintControlRegisterInstance instance, boolean state){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return;
        }
        node.setPrintDebugInformation(state);
    }
    public void setPrintErrorInfoState(InterfacePrintControlRegisterInstance instance, boolean state){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return;
        }
        node.setPrintErrorInformation(state);
    }
    //打印语句
    public boolean printlnErrorInfo(InterfacePrintControlRegisterInstance instance, String str){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return false;
        }
        if (node.isPrintErrorInformation()){
            str = "Error Info : " + str;
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean printlnLogicInfo(InterfacePrintControlRegisterInstance instance, String str){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return false;
        }
        if (node.isPrintLogicInformation()){
            str = "Logic Info : " + str;
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }
    public boolean printlnDebugInfo(InterfacePrintControlRegisterInstance instance, String str){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return false;
        }
        if (node.isPrintDebugInformation()){
            str = "Debug Info : " + str;
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }

    }
    public boolean printlnResultInfo(InterfacePrintControlRegisterInstance instance, String str){
        PrintControlNode node = this.getPrintControlNode(instance);
        if (node == null){
            this.errorPrint_UnRegister(instance);
            return false;
        }
        if (node.isPrintResultInformation()){
            str = "Result Info : " + str;
            System.out.println(str);
            return true;
        }
        else{
            return false;
        }
    }
    //错误输出
    private void errorPrint_UnRegister(InterfacePrintControlRegisterInstance instance){
            String error = "The "+instance.getClass().getName()+" is not registered, can't print information.";
            System.out.println(error);
    }
    //寻找输出控制节点
    private PrintControlNode getPrintControlNode(InterfacePrintControlRegisterInstance instance){
        if (this.nodes == null){
            return null;
        }
        for (int i = 0 ; i < this.nodes.length ; i++){
            if (this.nodes[i].getInstances() == instance){
                return this.nodes[i];
            }
        }
        return null;
    }
    //注册实例
    public boolean register(InterfacePrintControlRegisterInstance instance){
        if (this.isEistence(instance)){
            return false;
        }
        if (this.nodes == null){
            this.nodes = new PrintControlNode[1];
            this.nodes[0] = new PrintControlNode();
            this.nodes[0].setInstances(instance);
            return true;
        }
        else{
            PrintControlNode[] temp = new PrintControlNode[this.nodes.length+1];
            System.arraycopy(this.nodes,0,temp,0,this.nodes.length);
            temp[temp.length-1] = new PrintControlNode();
            temp[temp.length-1].setInstances(instance);
            this.nodes = temp;
            return true;
        }
    }
    //检查是否重复
    public boolean isEistence(InterfacePrintControlRegisterInstance instance){
        if (this.nodes == null){
            return false;
        }
        for (int i = 0 ; i < this.nodes.length ; i++){
            if (this.nodes[i].getInstances() == instance){
                return true;
            }
        }
        return false;
    }
}
