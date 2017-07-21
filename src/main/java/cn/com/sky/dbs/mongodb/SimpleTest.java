package cn.com.sky.dbs.mongodb;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;

//put方法返回的是value值，而append 方法返回的是对象本身，这样就可以向使用链式的方式添加数据。
//如:new BasicDBObject().append("username","zhang").append("password","111111");

public class SimpleTest {

	public static void main(String[] args) throws UnknownHostException,
			MongoException {

		// 建立mongoDB的链接
		Mongo mg = new Mongo("127.0.0.1", 7739);

		// 查询所有的Database
		for (String name : mg.getDatabaseNames()) {
			System.out.println("dbName: " + name);
		}

		DB db = mg.getDB("activity");
		// 查询所有的聚集集合
		for (String name : db.getCollectionNames()) {
			System.out.println("collectionName: " + name);
		}

		DBCollection coll = db.getCollection("user_score");

		SimpleTest st = new SimpleTest();
		// st.find(coll);
		// st.findCompare(coll);
		// st.insert();

		// ===count用法===//
		// // st.count(coll);
		// DBObject dbo=new BasicDBObject();
		// // dbo.put("createtime", "2014-02-20 18:00:21");
		//
		// DBObject dbo2=new BasicDBObject();
		// dbo2.put("$lte", "2014-02-20 18:00:21");
		// dbo.put("createtime", dbo2);
		// st.count(coll,dbo);

	}

	public void count(DBCollection coll) {
		long cnt = coll.count();
		System.out.println("coll.count(): " + cnt);
	}

	public void count(DBCollection coll, DBObject query) {
		long cnt = coll.count(query);
		System.out.println("coll.count(query): " + cnt);
	}

	public void find(DBCollection coll) {

		// // find()返回的是DBCursor游标对象。
		// // 查询所有的数据
		// DBCursor cur = coll.find();
		// while (cur.hasNext()) {
		// System.out.println(cur.next());
		// }
		// System.out.println(cur.count());
		//
		// // findOne()返回查询第一条记录
		// DBObject firstDoc = coll.findOne();
		// System.out.println(firstDoc);
		//
		// // 条件查询
		// DBCursor cur2 = coll.find(new BasicDBObject("user_id", 23556));
		// while (cur2.hasNext()) {
		// System.out.println(cur2.next());
		// }
		// System.out.println(cur2.count());

		// 条件查询2
		BasicDBObject condition = new BasicDBObject();
		condition.put("score", 2000);
		condition.put("user_id", 23556);
		DBCursor cur3 = coll.find(condition);
		while (cur3.hasNext()) {
			System.out.println(cur3.next());
		}
		System.out.println(cur3.count());

		// 查询部分数据块
		// DBCursor cursor = coll.find().skip(0).limit(10);
		// while (cursor.hasNext()) {
		// System.out.println(cursor.next());
		// }
	}

	public void findCompare(DBCollection coll) {
		// 比较符
		// "$gt"： 大于
		// "$gte"：大于等于
		// "$lt"： 小于
		// "$lte"：小于等于
		// "$in"： 包含
		// 比较查询(score > 10000)
		BasicDBObject condition = new BasicDBObject();
		condition.put("score", new BasicDBObject("$gt", 10000));

		DBCursor cur = coll.find(condition);
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());

		// 以下条件查询20000<score<=30000
		condition.put("score",
				new BasicDBObject("$gt", 20000).append("$lte", 30000));
		cur = coll.find(condition);
		while (cur.hasNext()) {
			System.out.println(cur.next());
		}
		System.out.println(cur.count());

		// 正则表达式
		Pattern pattern = Pattern.compile("1207794*", Pattern.CASE_INSENSITIVE);
		BasicDBObject query = new BasicDBObject("_id", pattern);
		DBCursor cursor = coll.find(query);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		System.out.println(cursor.count());
	}

	public void insert() {

		// 如何使用4种方式，将JSON数据插入到Mongodb中去。首先我们准备JSON
		// 　　格式的数据，如下：
		// 　　{
		// 　　"database" : "mkyongDB",
		// 　　"table" : "hosting",
		// 　　"detail" :
		// 　　{
		// 　　records : 99,
		// 　　index : "vps_index1",
		// 　　active : "true"
		// 　　}
		// 　　}
		// 　　}

		try {
			Mongo mongo = new Mongo("localhost", 27017);
			DB db = mongo.getDB("yourdb");
			DBCollection collection = db.getCollection("dummyColl");
			// BasicDBObject example
			System.out.println("BasicDBObject example...");
			BasicDBObject document = new BasicDBObject();
			document.put("database", "mkyongDB");
			document.put("table", "hosting");
			BasicDBObject documentDetail = new BasicDBObject();
			documentDetail.put("records", "99");
			documentDetail.put("index", "vps_index1");
			documentDetail.put("active", "true");
			document.put("detail", documentDetail);
			collection.insert(document);
			DBCursor cursorDoc = collection.find();
			while (cursorDoc.hasNext()) {
				System.out.println(cursorDoc.next());
			}
			collection.remove(new BasicDBObject());
			// BasicDBObjectBuilder example
			System.out.println("BasicDBObjectBuilder example...");
			BasicDBObjectBuilder documentBuilder = BasicDBObjectBuilder.start()
					.add("database", "mkyongDB").add("table", "hosting");
			BasicDBObjectBuilder documentBuilderDetail = BasicDBObjectBuilder
					.start().add("records", "99").add("index", "vps_index1")
					.add("active", "true");
			documentBuilder.add("detail", documentBuilderDetail.get());
			collection.insert(documentBuilder.get());
			DBCursor cursorDocBuilder = collection.find();
			while (cursorDocBuilder.hasNext()) {
				System.out.println(cursorDocBuilder.next());
			}
			collection.remove(new BasicDBObject());
			// Map example
			System.out.println("Map example...");
			Map documentMap = new HashMap();
			documentMap.put("database", "mkyongDB");
			documentMap.put("table", "hosting");
			Map documentMapDetail = new HashMap();
			documentMapDetail.put("records", "99");
			documentMapDetail.put("index", "vps_index1");
			documentMapDetail.put("active", "true");
			documentMap.put("detail", documentMapDetail);
			collection.insert(new BasicDBObject(documentMap));
			DBCursor cursorDocMap = collection.find();
			while (cursorDocMap.hasNext()) {
				System.out.println(cursorDocMap.next());
			}
			collection.remove(new BasicDBObject());
			// JSON parse example
			System.out.println("JSON parse example...");
			String json = "{'database' : 'mkyongDB','table' : 'hosting',"
					+ "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}}";
			DBObject dbObject = (DBObject) JSON.parse(json);
			collection.insert(dbObject);
			DBCursor cursorDocJSON = collection.find();
			while (cursorDocJSON.hasNext()) {
				System.out.println(cursorDocJSON.next());
			}
			collection.remove(new BasicDBObject());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
}