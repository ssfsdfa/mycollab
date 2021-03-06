/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.common.dao;

import com.esofthead.mycollab.common.domain.Currency;
import com.esofthead.mycollab.common.domain.CurrencyExample;
import com.esofthead.mycollab.core.persistence.ICrudGenericDAO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

@SuppressWarnings({ "ucd", "rawtypes" })
public interface CurrencyMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int countByExample(CurrencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int deleteByExample(CurrencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int insert(Currency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int insertSelective(Currency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    List<Currency> selectByExample(CurrencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    Currency selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByExampleSelective(@Param("record") Currency record, @Param("example") CurrencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByExample(@Param("record") Currency record, @Param("example") CurrencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByPrimaryKeySelective(Currency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    int updateByPrimaryKey(Currency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    Integer insertAndReturnKey(Currency value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_currency
     *
     * @mbggenerated Mon Dec 29 09:23:25 ICT 2014
     */
    void massUpdateWithSession(@Param("record") Currency record, @Param("primaryKeys") List primaryKeys);
}