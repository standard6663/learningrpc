package org.example.client;

import org.example.client.proxy.ClientProxy;
import org.example.common.pojo.User;
import org.example.common.service.UserService;

public class TestClient {
    public static void  main(String[] args){
        ClientProxy clientProxy=new ClientProxy("127.0.0.1",9999);
        System.out.println(clientProxy);
        UserService proxy=clientProxy.getProxy(UserService.class);
        System.out.println(proxy);
        User user = proxy.getUserByUserId(1);
        System.out.println("从服务端得到的user="+user.toString());

        User u=User.builder().id(100).userName("wxx").sex(true).build();
        Integer id = proxy.insertUserId(u);
        System.out.println("向服务端插入user的id"+id);
    }
}
