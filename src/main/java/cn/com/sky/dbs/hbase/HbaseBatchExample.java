package cn.com.sky.dbs.hbase;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Row;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseBatchExample {

	public static void main(String[] args) throws Exception {
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "testtable");
		List<Row> rowList = new ArrayList<Row>();
		Get get = new Get(Bytes.toBytes("row7"));
		get.addColumn(Bytes.toBytes("family1"), Bytes.toBytes("column7"));
		rowList.add(get);
		Delete delete = new Delete(Bytes.toBytes("row1"));
		rowList.add(delete);
		Put put = new Put(Bytes.toBytes("row9"));
		put.add(Bytes.toBytes("family1"), Bytes.toBytes("column6"), Bytes.toBytes("xxx"));
		rowList.add(put);
		Object[] result = new Object[rowList.size()];
		table.batch(rowList, result);
		for (Object ret : result) {
			System.out.println(ret);
		}
		table.close();

	}

}
