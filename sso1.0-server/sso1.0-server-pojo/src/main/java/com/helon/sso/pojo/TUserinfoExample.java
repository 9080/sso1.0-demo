package com.helon.sso.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * CreateDate:2017年12月22日下午1:55:07 
 * @Description: 用户信息Example  
 * @author:Helon
 * @version V1.0
 */
public class TUserinfoExample {
	protected String orderByClause;
	protected boolean distinct;
	protected List<Criteria> oredCriteria;
	protected Integer pageNo = Integer.valueOf(1);
	protected Integer startRow;
	protected Integer pageSize = Integer.valueOf(10);
	protected String fields;

	public TUserinfoExample() {
		this.oredCriteria = new ArrayList<>();
	}

	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
		startRow = Integer.valueOf((pageNo.intValue() - 1)
				* pageSize.intValue());
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		startRow = Integer.valueOf((pageNo.intValue() - 1)
				* pageSize.intValue());
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getFields() {
		return fields;
	}

	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			this.condition = condition;
			typeHandler = null;
			noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if ((value instanceof List))
				listValue = true;
			else
				singleValue = true;
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	public static class Criteria extends TUserinfoExample.GeneratedCriteria {
	}

	protected static abstract class GeneratedCriteria {
		protected List<TUserinfoExample.Criterion> criteria;

		protected GeneratedCriteria() {
			criteria = new ArrayList<>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<TUserinfoExample.Criterion> getAllCriteria() {
			return criteria;
		}

		public List<TUserinfoExample.Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new TUserinfoExample.Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new TUserinfoExample.Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if ((value1 == null) || (value2 == null)) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new TUserinfoExample.Criterion(condition, value1,
					value2));
		}

		public TUserinfoExample.Criteria andIdIsNull() {
			addCriterion("id is null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdEqualTo(Long value) {
			addCriterion("id =", value, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdNotEqualTo(Long value) {
			addCriterion("id <>", value, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdGreaterThan(Long value) {
			addCriterion("id >", value, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdGreaterThanOrEqualTo(Long value) {
			addCriterion("id >=", value, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdLessThan(Long value) {
			addCriterion("id <", value, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdLessThanOrEqualTo(Long value) {
			addCriterion("id <=", value, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdIn(List<Long> values) {
			addCriterion("id in", values, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdNotIn(List<Long> values) {
			addCriterion("id not in", values, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdBetween(Long value1, Long value2) {
			addCriterion("id between", value1, value2, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andIdNotBetween(Long value1,
				Long value2) {
			addCriterion("id not between", value1, value2, "id");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoIsNull() {
			addCriterion("account_no is null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoIsNotNull() {
			addCriterion("account_no is not null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoEqualTo(String value) {
			addCriterion("account_no =", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoNotEqualTo(String value) {
			addCriterion("account_no <>", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoGreaterThan(String value) {
			addCriterion("account_no >", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoGreaterThanOrEqualTo(
				String value) {
			addCriterion("account_no >=", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoLessThan(String value) {
			addCriterion("account_no <", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoLessThanOrEqualTo(
				String value) {
			addCriterion("account_no <=", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoLike(String value) {
			addCriterion("account_no like", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoNotLike(String value) {
			addCriterion("account_no not like", value, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoIn(List<String> values) {
			addCriterion("account_no in", values, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoNotIn(List<String> values) {
			addCriterion("account_no not in", values, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoBetween(String value1,
				String value2) {
			addCriterion("account_no between", value1, value2, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andAccountNoNotBetween(String value1,
				String value2) {
			addCriterion("account_no not between", value1, value2, "accountNo");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordIsNull() {
			addCriterion("password is null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordIsNotNull() {
			addCriterion("password is not null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordEqualTo(String value) {
			addCriterion("password =", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordNotEqualTo(String value) {
			addCriterion("password <>", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordGreaterThan(String value) {
			addCriterion("password >", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordGreaterThanOrEqualTo(
				String value) {
			addCriterion("password >=", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordLessThan(String value) {
			addCriterion("password <", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordLessThanOrEqualTo(
				String value) {
			addCriterion("password <=", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordLike(String value) {
			addCriterion("password like", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordNotLike(String value) {
			addCriterion("password not like", value, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordIn(List<String> values) {
			addCriterion("password in", values, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordNotIn(List<String> values) {
			addCriterion("password not in", values, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordBetween(String value1,
				String value2) {
			addCriterion("password between", value1, value2, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andPasswordNotBetween(String value1,
				String value2) {
			addCriterion("password not between", value1, value2, "password");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameIsNull() {
			addCriterion("user_name is null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameIsNotNull() {
			addCriterion("user_name is not null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameEqualTo(String value) {
			addCriterion("user_name =", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameNotEqualTo(String value) {
			addCriterion("user_name <>", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameGreaterThan(String value) {
			addCriterion("user_name >", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameGreaterThanOrEqualTo(
				String value) {
			addCriterion("user_name >=", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameLessThan(String value) {
			addCriterion("user_name <", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameLessThanOrEqualTo(
				String value) {
			addCriterion("user_name <=", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameLike(String value) {
			addCriterion("user_name like", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameNotLike(String value) {
			addCriterion("user_name not like", value, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameIn(List<String> values) {
			addCriterion("user_name in", values, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameNotIn(List<String> values) {
			addCriterion("user_name not in", values, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameBetween(String value1,
				String value2) {
			addCriterion("user_name between", value1, value2, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andUserNameNotBetween(String value1,
				String value2) {
			addCriterion("user_name not between", value1, value2, "userName");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexIsNull() {
			addCriterion("sex is null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexIsNotNull() {
			addCriterion("sex is not null");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexEqualTo(Boolean value) {
			addCriterion("sex =", value, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexNotEqualTo(Boolean value) {
			addCriterion("sex <>", value, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexGreaterThan(Boolean value) {
			addCriterion("sex >", value, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexGreaterThanOrEqualTo(
				Boolean value) {
			addCriterion("sex >=", value, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexLessThan(Boolean value) {
			addCriterion("sex <", value, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexLessThanOrEqualTo(Boolean value) {
			addCriterion("sex <=", value, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexIn(List<Boolean> values) {
			addCriterion("sex in", values, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexNotIn(List<Boolean> values) {
			addCriterion("sex not in", values, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexBetween(Boolean value1,
				Boolean value2) {
			addCriterion("sex between", value1, value2, "sex");
			return (TUserinfoExample.Criteria) this;
		}

		public TUserinfoExample.Criteria andSexNotBetween(Boolean value1,
				Boolean value2) {
			addCriterion("sex not between", value1, value2, "sex");
			return (TUserinfoExample.Criteria) this;
		}
	}
}