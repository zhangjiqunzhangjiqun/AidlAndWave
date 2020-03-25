// MessageCenter.aidl
package com.itep.client;
import  com.itep.client.Info;
// Declare any non-default types here with import statements
interface MessageCenter {
   List<Info> getInfo();

       //传参时除了Java基本类型以及String，CharSequence之外的类型
       //都需要在前面加上定向tag，具体加什么量需而定
       void addInfo(inout Info info);

}
