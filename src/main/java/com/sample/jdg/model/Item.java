/**
 * 
 */
package com.sample.jdg.model;

import java.util.Date;
import java.util.List;

/**
 * @author vpogu
 *
 */
public class Item {
	private String itemId;
	private String itemDesc;
	private Date endingDate;
	private List<BidInfo> bidInfo;

	public Item() {
		super();
	}

	public Item(String itemId, String itemDesc, Date endingDate, List<BidInfo> bidInfo) {
		super();
		this.itemId = itemId;
		this.itemDesc = itemDesc;
		this.endingDate = endingDate;
		this.bidInfo = bidInfo;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public List<BidInfo> getBidInfo() {
		return bidInfo;
	}

	public void setBidInfo(List<BidInfo> bidInfo) {
		this.bidInfo = bidInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bidInfo == null) ? 0 : bidInfo.hashCode());
		result = prime * result + ((endingDate == null) ? 0 : endingDate.hashCode());
		result = prime * result + ((itemDesc == null) ? 0 : itemDesc.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (bidInfo == null) {
			if (other.bidInfo != null)
				return false;
		} else if (!bidInfo.equals(other.bidInfo))
			return false;
		if (endingDate == null) {
			if (other.endingDate != null)
				return false;
		} else if (!endingDate.equals(other.endingDate))
			return false;
		if (itemDesc == null) {
			if (other.itemDesc != null)
				return false;
		} else if (!itemDesc.equals(other.itemDesc))
			return false;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		return true;
	}

}
