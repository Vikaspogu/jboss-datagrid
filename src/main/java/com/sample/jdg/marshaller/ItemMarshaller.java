/**
 * 
 */
package com.sample.jdg.marshaller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.infinispan.protostream.MessageMarshaller;

import com.sample.jdg.model.BidInfo;
import com.sample.jdg.model.Item;

/**
 * @author vpogu
 *
 */
public class ItemMarshaller implements MessageMarshaller<Item> {

	@Override
	public Class<? extends Item> getJavaClass() {
		return Item.class;
	}

	@Override
	public String getTypeName() {
		return "model.Item";
	}

	@Override
	public Item readFrom(ProtoStreamReader reader) throws IOException {
		String itemId = reader.readString("itemId");
		String itemDesc = reader.readString("itemDesc");
		Date endingDate = new Date(reader.readLong("endingDate"));
		List<BidInfo> bidInfo = reader.readCollection("bidInfo", new ArrayList<BidInfo>(), BidInfo.class);
		return new Item(itemId, itemDesc, endingDate, bidInfo);
	}

	@Override
	public void writeTo(ProtoStreamWriter writer, Item item) throws IOException {
		writer.writeString("itemId", item.getItemId());
		writer.writeString("itemDesc", item.getItemDesc());
		writer.writeLong("endingDate", item.getEndingDate().getTime());
		writer.writeCollection("bidInfo", item.getBidInfo(), BidInfo.class);
	}

}
