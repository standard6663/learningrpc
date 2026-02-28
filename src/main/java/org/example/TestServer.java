package org.example;

import org.example.Server.provider.ServiceProvider;
import org.example.Server.server.Impl.SimpleRPCRPCServer;
import org.example.Server.server.RpcServer;
import org.example.common.service.Impl.UserServiceImpl;
import org.example.common.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);

        RpcServer rpcServer = new SimpleRPCRPCServer(serviceProvider);
        rpcServer.start(9999);
    }
}