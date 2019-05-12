//以下被註解的第9行、15行、28行為原Hibernate3-4的做法

package generator;

import java.io.*;
import java.sql.*;

import org.hibernate.HibernateException;
//import org.hibernate.engine.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import android.com.goodsorder.model.GoodsOrderVO;

public class MyGenerator implements IdentifierGenerator {

//	public Serializable generate(SessionImplementor session, Object object)
	public Serializable generate(SharedSessionContractImplementor session, Object object)
			throws HibernateException {

		String prefix = GeneratorsType.prefix.get(object.getClass().toGenericString());
		String zero4 = "0000";
		String zero3 = "000";
		
		String VOname = object.getClass().getSimpleName();
		String sequenceName = VOname.substring(0, VOname.length()-2);
		
		
		String Id = null;
		Connection con = session.connection();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT "+sequenceName+"_seq.NEXTVAL as nextval FROM DUAL");
			rs.next();
			int nextval = rs.getInt("nextval");
			
			if(nextval/10 > 0)
				Id = prefix + zero3 + nextval;
			else
				Id = prefix + zero4 + nextval;
			
//			con.close();   //於Hibernate 5 不可關閉連線
		} catch (SQLException e) {
			throw new HibernateException("Unable to generate Sequence");
		}
		return Id;
	}
}