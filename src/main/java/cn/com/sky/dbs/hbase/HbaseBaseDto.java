package cn.com.sky.dbs.hbase;

public abstract class HbaseBaseDto extends BaseDto {

	/** @Fields serialVersionUID: */
	private static final long serialVersionUID = -411634609847958302L;

	public HbaseBaseDto(String rowKey) {
		setRowKey(rowKey);
	}

	private String tableName;// tableName

	private String rowKey;// hbaseRowKey

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public abstract String defaultTableName();
}
