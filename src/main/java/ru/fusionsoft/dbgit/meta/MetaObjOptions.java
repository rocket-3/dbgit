package ru.fusionsoft.dbgit.meta;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import ru.fusionsoft.dbgit.adapters.AdapterFactory;
import ru.fusionsoft.dbgit.adapters.IDBAdapter;
import ru.fusionsoft.dbgit.core.DBGitLang;
import ru.fusionsoft.dbgit.core.ExceptionDBGit;
import ru.fusionsoft.dbgit.core.ExceptionDBGitObjectNotFound;
import ru.fusionsoft.dbgit.dbobjects.DBOptionsObject;
import ru.fusionsoft.dbgit.dbobjects.DBUser;
import ru.fusionsoft.dbgit.utils.CalcHash;

/**
 * Base Meta class for data use DBOptionsObject information. This data is tree string properties.
 * @author mikle
 *
 */
public abstract class MetaObjOptions extends MetaBase {

	private DBOptionsObject objectOption = null;
	
	public MetaObjOptions() {
		setDbType();
		setDbVersion();
	}
	
	public MetaObjOptions(DBOptionsObject objectOption) throws ExceptionDBGit {
		this();
		setObjectOption(objectOption);
	}
	
	public DBOptionsObject getObjectOption() {
		return objectOption;
	}

	public void setObjectOption(DBOptionsObject objectOption) throws ExceptionDBGit {
		this.objectOption = objectOption;
		setName(objectOption.getName()+"."+getType().getValue());
	}

	@Override
	public boolean serialize(OutputStream stream) throws IOException {
		return yamlSerialize(stream);
	}

	@Override
	public IMetaObject deSerialize(InputStream stream) throws IOException{
		return yamlDeSerialize(stream);
	}


	@Override
	public String getHash() {
		CalcHash ch = new CalcHash();
		ch.addData(this.getName());
		ch.addData(this.getType().getValue());
		ch.addData(objectOption != null ? objectOption.getHash() : EMPTY_HASH);

		return ch.calcHashStr();
	}
	
	/**
	 * select from map object with current name MetaObject and set ObjectOption
	 * @param map
	 */
	public void setObjectOptionFromMap(Map<String, ? extends DBOptionsObject> map) throws ExceptionDBGit {
		NameMeta nm = MetaObjectFactory.parseMetaName(getName());
		if (!map.containsKey(nm.getName())) {
			throw new ExceptionDBGitObjectNotFound(DBGitLang.getInstance().getValue("errors", "meta", "notFound").withParams(getName()));
		}
		setObjectOption(map.get(nm.getName()));
	}

}
