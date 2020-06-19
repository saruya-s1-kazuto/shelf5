package main.form;

public class Common {
	public enum Mode{
		
		DISP("表示"),
		REGIST("登録"),
		DELETE("削除");
		
		private String name;
		private Mode(String name) {
			this.name=name;
		}
		public String getName() {
			return this.name;
		}
		
	}
}
