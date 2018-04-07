package ru.fusionsoft.dbgit.adapters;

import java.sql.Connection;

import ru.fusionsoft.dbgit.meta.IMetaObject;


/**
 * <div class="en">Adapter for database recovery. For each type of meta description, you can use your recovery adapter.</div>
 * <div class="ru">Адаптер для восстановления БД. Для каждого типа метаописания возможен свой адаптер восстановления. </div>
 * 
 * @author mikle
 *
 *
 */
public interface IDBAdapterRestoreMetaData {
	public void setAdapter(IDBAdapter adapter);
	public void restoreMetaObject(IMetaObject obj);
	public void removeMetaObject(IMetaObject obj);
}