import com.vladbondar.DBConnect;
import org.junit.Test;

import static junit.framework.Assert.assertSame;

/**
 * Created by Влад on 04.12.2015.
 */
public class TestWork {
    DBConnect connect = new DBConnect();

    @Test
    public void addUpdateDeleteTest(){
        assertSame(connect.addToDataBase("Nikolay", "2010-10-10",
                "0123456789"),1);
        assertSame(connect.updateValueByName("Nikolay", "Nikolayyyy",
                "2011-11-11", "0987654321"),1);
        assertSame(connect.deleteFromDataBaseByName("Nikolayyyy"),1);
    }

}
