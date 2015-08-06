package cn.edu.cqnu.forconsumer.spider.hibernate;

import java.util.HashSet;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import cn.edu.cqnu.forconsumer.spider.model.Product;
import cn.edu.cqnu.forconsumer.spider.model.Seed;
import cn.edu.cqnu.forconsumer.spider.model.Sl;
import cn.edu.cqnu.forconsumer.util.Constant;

public class DBManager {
	
	private static SessionFactory factory = null; 
	private static int SEED_INIT_NUM = 500;
	private static Logger log = null;
	
	static {
		factory =  new Configuration().configure().buildSessionFactory();
		log = LogManager.getLogger(DBManager.class.getName());
	}
	
	/**
	 * 增加
	 * @param seed
	 * @return
	 */
	public Integer addSeed(Seed seed){
		Session session = factory.openSession();
		Transaction ts = null ;
		Integer id = null ;
		try{
			ts = session.beginTransaction();
			id = (Integer) session.save(seed);
			ts.commit();
		}
		catch(HibernateException he){
			if(ts != null)
				ts.rollback();
			//he.printStackTrace();
			log.error(he.getMessage());
		}
		finally{
			session.close();
		}
		return id;
	}

	public HashSet<String> getSeed(String strOwn){
		return getSeed(0,SEED_INIT_NUM,strOwn);
	}
	
	/**
	 * 获取种子
	 * @param iStart
	 * @param iNum
	 * @return
	 */
	public HashSet<String> getSeed(int iStart, int iNum, String strOwn){
		Session session =  factory.openSession();
		Transaction tran = null;
		try{
			tran = session.beginTransaction();
			HashSet<String> seedSet = new HashSet<String>();
			ScrollableResults seeds = session.getNamedQuery("callSelectSeedSP")
							.setParameter("start", iStart)
							.setParameter("num", iNum)
							.setParameter("vown", strOwn)
							.setCacheMode(CacheMode.IGNORE)
							.scroll(ScrollMode.FORWARD_ONLY);
			int count = 0;
			while(seeds.next()){
				Seed seed = (Seed)seeds.get(0);
				seedSet.add(seed.getLink());
				seed.setIs_visited((byte)1);
				session.update(seed);
			    if(++count%20 == 0){
			    	 //flush a batch of updates and release memory:
			        session.flush();
			        session.clear();
			    }
			}
			
			tran.commit();
			return seedSet;
		}catch(HibernateException he){
			if(tran!=null)
				tran.rollback();
			log.error(he.getMessage());
			return null;
		}finally{
			session.close();
		}
	}
	
	/**
	 * 插入种子到数据库
	 * @param hsSeed
	 * @return
	 */
	public boolean insertSeed(HashSet<String> hsSeed){
		Session session =  factory.openSession();
		Transaction tran = null;
		try{
			tran = session.beginTransaction();
		   Iterator<String> itr =  hsSeed.iterator();
		   int count = 0;
		   while(itr.hasNext()){
			   Seed seed = new Seed();
			   seed.setIs_valid(Constant.IS_VALID);
			   seed.setIs_visited(Constant.IS_UN_VISITED);
			   seed.setLink(itr.next());
			   if(seed.getLink().indexOf(Constant.JINGDONG_OWN) > 0)
				   seed.setOwn(Constant.JINGDONG);
			   else if(seed.getLink().indexOf(Constant.DANGDANG_OWN) > 0)
				   seed.setOwn(Constant.DANGDANG);
			   session.save(seed);
			   if(++count%20 == 0){//20, same as the JDBC batch size
			        //flush a batch of inserts and release memory:
			        session.flush();
			        session.clear();
			   }
		   }
			
			tran.commit();
			return true;
		}catch(HibernateException he){
			if(tran!=null)
				tran.rollback();
			log.error(he.getMessage());
			return false;
		}finally{
			session.close();
		}
	}
	
	/**
	 * 插入商品数据
	 * @param hsProduct
	 * @return
	 */
	public boolean insertProduct(HashSet<String> hsProduct){
		Session session =  factory.openSession();
		Transaction tran = null;
		try{
			tran = session.beginTransaction();
		   Iterator<String> itr =  hsProduct.iterator();
		   int count = 0;
		   while(itr.hasNext()){
			   String link = itr.next();
			   Product product  = session.get(Product.class, link);
			   //在数据库中未找到，则加入，如果存在，则返回一个实例
			   if(product == null){
				   product = new Product();
				   product.setIs_valid(Constant.IS_VALID);
				   product.setIs_visited(Constant.IS_UN_VISITED);
				   product.setLink(link);
				   if(link.indexOf(Constant.JINGDONG_OWN) > 0)
					   product.setOwn(Constant.JINGDONG);
				   else if(link.indexOf(Constant.DANGDANG_OWN) > 0)
					   product.setOwn(Constant.DANGDANG);
				   product.setProduct_id(link.replace("http://item.jd.com/", "").replace(".html", ""));
				   //product.setTitle(title);
				   session.save(product);
				   if(++count%20 == 0){//20, same as the JDBC batch size
				        //flush a batch of inserts and release memory:
				        session.flush();
				        session.clear();
				   }
			   }
		   }
			
			tran.commit();
			return true;
		}catch(HibernateException he){
			if(tran!=null)
				tran.rollback();
			log.error(he.getMessage());
			return false;
		}finally{
			session.close();
		}
	}
	
}
