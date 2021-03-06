package com.futuo.iapprove.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.futuo.iapprove.R;
import com.futuo.iapprove.provider.LocalStorageDBHelper.LocalStorageDataDirtyType;
import com.futuo.iapprove.provider.UserEnterpriseTodoListTaskContentProvider.TodoTaskFormItems.TodoTaskFormItem;
import com.richitec.commontoolkit.CTApplication;
import com.richitec.commontoolkit.utils.JSONUtils;

public class IApproveTaskFormItemBean implements
		Comparable<IApproveTaskFormItemBean>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5889519437707449801L;

	private static final String LOG_TAG = IApproveTaskFormItemBean.class
			.getCanonicalName();

	// row id, task form item id, form item name, info and is capital
	private Long rowId;
	private Long itemId;
	private String itemName;
	private String itemInfo;
	private Boolean itemCapital;

	// task form item physical name
	private String itemPhysicalName;

	// local storage data dirty type
	private LocalStorageDataDirtyType lsDataDirtyType;

	public IApproveTaskFormItemBean() {
		super();

		// set default capital flag
		itemCapital = false;

		// set default local storage data dirty type
		lsDataDirtyType = LocalStorageDataDirtyType.NORMAL;
	}

	// constructor with JSON object
	public IApproveTaskFormItemBean(JSONObject taskFormItemJSONObject) {
		this();

		// check task form item JSON object
		if (null != taskFormItemJSONObject) {
			// get application context
			Context _appContext = CTApplication.getContext();

			// set task form item attributes
			// task form item id
			try {
				itemId = Long
						.parseLong(JSONUtils
								.getStringFromJSONObject(
										taskFormItemJSONObject,
										_appContext
												.getResources()
												.getString(
														R.string.rbgServer_getIApproveListTaskFormInfoReqResp_item_id)));
			} catch (NumberFormatException e) {
				Log.e(LOG_TAG,
						"Get task form item id error, exception message = "
								+ e.getMessage());

				e.printStackTrace();
			}

			// task form item name
			itemName = JSONUtils
					.getStringFromJSONObject(
							taskFormItemJSONObject,
							_appContext
									.getResources()
									.getString(
											R.string.rbgServer_getIApproveListTaskFormInfoReqResp_item_name));

			// task form item capital flag
			try {
				// get task item capital flag
				Integer _capitalFlag = Integer
						.parseInt(JSONUtils
								.getStringFromJSONObject(
										taskFormItemJSONObject,
										_appContext
												.getResources()
												.getString(
														R.string.rbgServer_getIApproveListTaskFormInfoReqResp_item_capitalFlag)));

				if (Integer
						.parseInt(_appContext
								.getResources()
								.getString(
										R.string.rbgServer_getIApproveListTaskFormInfoReqResp_item_capital)) == _capitalFlag) {
					itemCapital = true;
				} else if (Integer
						.parseInt(_appContext
								.getResources()
								.getString(
										R.string.rbgServer_getIApproveListTaskFormInfoReqResp_item_notCapital)) == _capitalFlag) {
					itemCapital = false;
				}
			} catch (NumberFormatException e) {
				Log.e(LOG_TAG,
						"Get task form item capital flag error, exception message = "
								+ e.getMessage());

				e.printStackTrace();
			}

			// task form item physical name
			itemPhysicalName = JSONUtils
					.getStringFromJSONObject(
							taskFormItemJSONObject,
							_appContext
									.getResources()
									.getString(
											R.string.rbgServer_getIApproveListTaskFormInfoReqResp_item_physicalName));
		} else {
			Log.e(LOG_TAG,
					"New iApprove task form item with JSON object error, form JSON object = "
							+ taskFormItemJSONObject);
		}
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(String itemInfo) {
		this.itemInfo = itemInfo;
	}

	public Boolean itemNeedCapital() {
		return itemCapital;
	}

	public void setItemCapital(Boolean itemCapital) {
		this.itemCapital = itemCapital;
	}

	public String getItemPhysicalName() {
		return itemPhysicalName;
	}

	public void setItemPhysicalName(String itemPhysicalName) {
		this.itemPhysicalName = itemPhysicalName;
	}

	public LocalStorageDataDirtyType getLocalStorageDataDirtyType() {
		return lsDataDirtyType;
	}

	public void setLocalStorageDataDirtyType(
			LocalStorageDataDirtyType lsDataDirtyType) {
		this.lsDataDirtyType = lsDataDirtyType;
	}

	@Override
	public int compareTo(IApproveTaskFormItemBean another) {
		int _result = -1;

		// check iApprove task form item name and info
		if ((null == itemName && null == another.itemName)
				|| (null != itemName && null != another.itemName && itemName
						.equalsIgnoreCase(another.itemName))) {
			if ((null == itemInfo && null == another.itemInfo)
					|| (null != itemInfo && null != another.itemInfo && itemInfo
							.equalsIgnoreCase(another.itemInfo))) {
				_result = 0;
			} else {
				Log.d(LOG_TAG,
						"IApprove task form item info not equals, self info = "
								+ itemInfo + " and another info = "
								+ another.itemInfo);
			}
		} else {
			Log.d(LOG_TAG,
					"IApprove task form item name not equals, self name = "
							+ itemName + " and another name = "
							+ another.itemName);
		}

		return _result;
	}

	@Override
	public String toString() {
		// define description
		StringBuilder _description = new StringBuilder();

		// append user enterprise iApprove task form item id, name, physical
		// name and info
		_description
				.append("User enterprise iApprove task form item row id = ")
				.append(rowId).append(", ").append("form item id = ")
				.append(itemId).append(", ").append("form item name = ")
				.append(itemName).append(", ")
				.append("form item physical name = ").append(itemPhysicalName)
				.append(", ").append(" and ").append("info = ")
				.append(itemInfo);

		return _description.toString();
	}

	// get to-do list task form item with cursor
	public static IApproveTaskFormItemBean getTaskFormItem(Cursor cursor) {
		// new to-do list task form item
		IApproveTaskFormItemBean _todoTaskFormItem = new IApproveTaskFormItemBean();

		// check the cursor
		if (null != cursor) {
			// set to-do list task form item attributes
			// row id
			_todoTaskFormItem.rowId = cursor.getLong(cursor
					.getColumnIndex(TodoTaskFormItem._ID));

			// task form item id
			_todoTaskFormItem.itemId = cursor.getLong(cursor
					.getColumnIndex(TodoTaskFormItem.ITEM_ID));

			// task form item name
			_todoTaskFormItem.itemName = cursor.getString(cursor
					.getColumnIndex(TodoTaskFormItem.NAME));

			// task form item info
			_todoTaskFormItem.itemInfo = cursor.getString(cursor
					.getColumnIndex(TodoTaskFormItem.INFO));

			// task form item need capital
			_todoTaskFormItem.itemCapital = 0 == cursor.getShort(cursor
					.getColumnIndex(TodoTaskFormItem.CAPITAL_FLAG)) ? false
					: true;
		} else {
			Log.e(LOG_TAG,
					"Get to-do list task form item with cursor error, cursor = "
							+ cursor);
		}

		return _todoTaskFormItem;
	}

	// get iApprove task form item list with JSON object
	public static List<IApproveTaskFormItemBean> getTaskFormItems(
			JSONObject taskContentInfoJSONObject) {
		List<IApproveTaskFormItemBean> _taskFormItems = null;

		// check iApprove task content info JSON object
		if (null != taskContentInfoJSONObject) {
			// get application context
			Context _appContext = CTApplication.getContext();

			// initialize return iApprove task form item bean list
			_taskFormItems = new ArrayList<IApproveTaskFormItemBean>();

			// get and check iApprove task content info json array
			JSONArray _taskContentInfosJSONArray = JSONUtils
					.getJSONArrayFromJSONObject(
							taskContentInfoJSONObject,
							_appContext
									.getResources()
									.getString(
											R.string.rbgServer_getIApproveListTaskFormInfoReqResp_contentList));
			if (null != _taskContentInfosJSONArray
					&& 0 < _taskContentInfosJSONArray.length()) {
				for (int i = 0; i < _taskContentInfosJSONArray.length(); i++) {
					// get each iApprove task content info and check its type
					JSONObject _taskContentInfoJSONObject = JSONUtils
							.getJSONObjectFromJSONArray(
									_taskContentInfosJSONArray, i);
					if (null != _taskContentInfoJSONObject
							&& _appContext
									.getResources()
									.getString(
											R.string.rbgServer_getIApproveListTaskFormInfoReqResp_formContentType)
									.equalsIgnoreCase(
											JSONUtils
													.getStringFromJSONObject(
															_taskContentInfoJSONObject,
															_appContext
																	.getResources()
																	.getString(
																			R.string.rbgServer_getIApproveListTaskFormInfoReqResp_contentType)))) {
						// get, check iApprove task form item and its value json
						// array
						JSONArray _taskFormItemsJSONArray = JSONUtils
								.getJSONArrayFromJSONObject(
										_taskContentInfoJSONObject,
										_appContext
												.getResources()
												.getString(
														R.string.rbgServer_getIApproveListTaskFormInfoReqResp_itemList));
						JSONArray _taskFormItemValuesJSONArray = JSONUtils
								.getJSONArrayFromJSONObject(
										_taskContentInfoJSONObject,
										_appContext
												.getResources()
												.getString(
														R.string.rbgServer_getIApproveListTaskFormInfoReqResp_itemValueList));

						if (null != _taskFormItemsJSONArray
								&& null != _taskFormItemValuesJSONArray) {
							// get user enterprise iApprove task form item
							// values json
							// object
							JSONObject _taskFormItemValuesJSONObject = JSONUtils
									.getJSONObjectFromJSONArray(
											_taskFormItemValuesJSONArray, 0);

							for (int j = 0; j < _taskFormItemsJSONArray
									.length(); j++) {
								// get user enterprise iApprove task form item
								IApproveTaskFormItemBean _taskFormItem = new IApproveTaskFormItemBean(
										JSONUtils.getJSONObjectFromJSONArray(
												_taskFormItemsJSONArray, j));

								// get and check user enterprise iApprove task
								// form item
								// value
								String _taskFormItemValue = JSONUtils
										.getStringFromJSONObject(
												_taskFormItemValuesJSONObject,
												_taskFormItem
														.getItemPhysicalName()
														.toLowerCase(Locale.US));
								if (null != _taskFormItemValue
										&& !"".equalsIgnoreCase(_taskFormItemValue)) {
									_taskFormItem
											.setItemInfo(_taskFormItemValue);

									// add got user enterprise iApprove task
									// form item to
									// list
									_taskFormItems.add(_taskFormItem);
								}
							}
						}
					}
				}
			}
		} else {
			Log.e(LOG_TAG,
					"Get iApprove task form item list with JSON object error, content info JSON object = "
							+ taskContentInfoJSONObject);
		}

		return _taskFormItems;
	}

}
