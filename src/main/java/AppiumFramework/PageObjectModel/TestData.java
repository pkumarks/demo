package AppiumFramework.PageObjectModel;

import org.testng.annotations.DataProvider;

public class TestData {
	@DataProvider(name="InputData")
	public Object[][] getData() {
		Object[][] obj = new Object[][]{
			
			{"hello"},{"$%$%"}
			
		};
		return obj;
	}

}
