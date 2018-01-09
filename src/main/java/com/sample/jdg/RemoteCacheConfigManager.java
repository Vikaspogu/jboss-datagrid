package com.sample.jdg;

import java.io.IOException;
import java.io.InputStream;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;
import org.infinispan.commons.util.Util;
import org.infinispan.protostream.DescriptorParserException;
import org.infinispan.protostream.FileDescriptorSource;
import org.infinispan.protostream.SerializationContext;

import com.sample.jdg.marshaller.BidInfoMarshaller;
import com.sample.jdg.marshaller.ItemMarshaller;

public class RemoteCacheConfigManager {

	private RemoteCache<String, String> cache;

	public RemoteCacheConfigManager() {
		super();
		ConfigurationBuilder builder = new ConfigurationBuilder().addServer().host("127.0.0.1").port(11322)
				.marshaller(new ProtoStreamMarshaller());

		builder.security().authentication().enable().serverName("myhotrodserver").saslMechanism("DIGEST-MD5")
				.callbackHandler(new MyCallbackHandler("myuser", "ApplicationRealm", "qwer1234!".toCharArray()));
		RemoteCacheManager remoteCacheManager = new RemoteCacheManager(builder.build());
		cache = remoteCacheManager.getCache("secured");

		SerializationContext serCtx = ProtoStreamMarshaller.getSerializationContext(remoteCacheManager);
		try {
			serCtx.registerProtoFiles(FileDescriptorSource.fromResources("model/item.proto"));
		} catch (DescriptorParserException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serCtx.registerMarshaller(new ItemMarshaller());
		serCtx.registerMarshaller(new BidInfoMarshaller());

	}

	public static void registerProtofile(String jmxHost, int jmxPort, String cacheConatiner) throws Exception {
		JMXConnector jmxConnector = JMXConnectorFactory
				.connect(new JMXServiceURL("service:jmx:remoting-jmx://" + "localhost" + ":" + 9999));
		MBeanServerConnection jmxConnection = jmxConnector.getMBeanServerConnection();

		ObjectName protobufMetadataManagerObjName = new ObjectName("jboss.infinispan:type=RemoteQuery,name="
				+ ObjectName.quote("local") + ",component=ProtobufMetadataManager");

		// initialize client-side serialization context via JMX
		byte[] descriptor = readClasspathResource("/model/item.protobin");
		jmxConnection.invoke(protobufMetadataManagerObjName, "registerProtofile", new Object[] { descriptor },
				new String[] { byte[].class.getName() });
		jmxConnector.close();
	}

	public static byte[] readClasspathResource(String c) throws IOException {
		InputStream is = RemoteCacheConfigManager.class.getResourceAsStream(c);
		try {
			return Util.readStream(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

}
