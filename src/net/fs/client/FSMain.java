// Copyright (c) 2015 D1SM.net
package net.fs.client;

public class FSMain {

    public static void main(String[] args) {
        if(args.length<1){
            System.out.printf("Usage: java -jar finalspeed.jar config_path");
            return;
        }
        new Client(args[0]);
    }
}
