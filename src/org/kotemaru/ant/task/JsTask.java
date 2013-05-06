package org.kotemaru.ant.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Parameter;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.apache.tools.ant.types.resources.FileResource;
import org.mozilla.javascript.*;

public class JsTask extends Task  {

	private List<ResourceCollection> rclist = new ArrayList<ResourceCollection>();
	private List<Parameter> params = new ArrayList<Parameter>();
	private String text;
	
	public void add(ResourceCollection rc) {
		rclist.add(rc);
	}
	public void addParam(Parameter param) {
		params.add(param);
	}
	public void addText(String str) {
		text = str;
	}
	
	@Override
	public void execute() throws BuildException {
		Context cx = Context.enter();
		try {
			Scriptable scope = cx.initStandardObjects();
			scope.put("task", scope, this);
			scope.put("fileset", scope, toFileList(rclist));
			for (Parameter param : params) {
				scope.put(param.getName(), scope, param.getValue());
			}
			cx.evaluateString(scope, text, "ant.js", 0, null);
		} finally {
			Context.exit();
		}
	}	
	
	private List<File> toFileList(List<ResourceCollection> rclist) {
		List<File> filelist = new ArrayList<File>(); 
		for (ResourceCollection rc : rclist) {
			for (Iterator<?> ite = rc.iterator(); ite.hasNext();) {
				Resource resource = (Resource) ite.next();
				if (resource instanceof FileResource) {
					File file = ((FileResource) resource).getFile();
					filelist.add(file);
				}
			}
		}
		return filelist;
	}
}