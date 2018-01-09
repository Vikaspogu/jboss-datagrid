/**
 * 
 */
package com.sample.jdg.marshaller;

import java.io.IOException;

import org.infinispan.protostream.MessageMarshaller;

import com.sample.jdg.model.BidInfo;

/**
 * @author vpogu
 *
 */
public class BidInfoMarshaller implements MessageMarshaller<BidInfo> {

	@Override
	public Class<? extends BidInfo> getJavaClass() {
		return BidInfo.class;
	}

	@Override
	public String getTypeName() {
		return "mode.BidInfo";
	}

	@Override
	public BidInfo readFrom(ProtoStreamReader reader) throws IOException {
		return new BidInfo(reader.readString("bidderName"), reader.readDouble("bidderAmount"));
	}

	@Override
	public void writeTo(ProtoStreamWriter writer, BidInfo bid) throws IOException {
		writer.writeString("bidderName", bid.getBidderName());
		writer.writeDouble("bidderAmount", bid.getBidderAmount());
	}

}
