package implementations;

import interfaces.DataInt;

public class WcMessage implements DataInt{

	private static final long serialVersionUID = 1L;
	private String area;
	private String datafield;
	
	public WcMessage(String area, String datafield){
		this.area = area;
		this.datafield = datafield;
	}

	@Override
	public String getData() {
		return this.datafield;
		
	}

	@Override
	public String getArea() {
		return this.area;
	}

}
