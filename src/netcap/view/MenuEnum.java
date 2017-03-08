package netcap.view;

public enum MenuEnum {
	START("start", "开始"), 
	STOP("stop", "停止"), 
	SAVE("save", "保存"), 
	SAVEAS("saveAs", "保存为..."), 
	EXIT("exit", "退出"), 
	CONF("conf", "配置");
	
	private String command;
	private String description;
	
	private MenuEnum(String command, String description){
		this.command = command;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	public static MenuEnum getMenuEnum(String command){
		for (MenuEnum e : MenuEnum.values()) {
            if(e.getCommand().equals(command))
            	return e;
        }
		return null;
	}
}
