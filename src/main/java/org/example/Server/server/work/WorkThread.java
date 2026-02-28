package org.example.Server.server.work;

import lombok.AllArgsConstructor;
import org.example.Server.provider.ServiceProvider;
import org.example.common.Message.RpcRequest;
import org.example.common.Message.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try{
            // 1. 创建输入输出流
            ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
            ////读取客户端传过来的request
            RpcRequest rpcRequest = (RpcRequest) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private RpcResponse getResponse(RpcRequest rpcRequest){//获取返回的信息
        //得到服务名
        String interfaceName=rpcRequest.getInterfaceName();
        //得到服务对应的实现类
        Object service = serviceProvider.getService(interfaceName);
        //
        Method method=null;
        try{
            method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamsType());
            Object invoke = method.invoke(service, rpcRequest.getParams());


            return RpcResponse.sussess(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RpcResponse.fail();
        }
    }
}
