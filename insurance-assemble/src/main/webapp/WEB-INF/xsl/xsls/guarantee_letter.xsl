<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format">
    <!-- 根元素  -->


    <xsl:template match="/">
        <xsl:apply-templates select="EntryDailyListReport"/>
    </xsl:template>

    <!--主模板//-->
    <xsl:template match="EntryDailyListReport">
        <xsl:processing-instruction name="cocoon-format">type="text/xslfo"</xsl:processing-instruction>
        <!--在此可以定义一些全局的风格信息，如字体等-->
        <fo:root font-family="KaiTi_GB2312" xmlns:fo="http://www.w3.org/1999/XSL/Format">

            <!--版面定义//-->
            <fo:layout-master-set>
                <fo:simple-page-master
                    page-master-name="main"
                    margin-top="2cm"
                    margin-bottom="2cm"
                    margin-left="2cm"
                    margin-right="1.5cm">
                    <!--页眉//-->
                    <fo:region-before extent="0cm"/>
                    <!--主体//-->
                    <fo:region-body margin-top="0cm" margin-bottom="1cm"/>
                    <!--页脚//-->
                    <fo:region-after extent="0.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:apply-templates select="PageList"/>
        </fo:root>
    </xsl:template>
    

    <xsl:template match="PageList">
        <fo:page-sequence>
            <!--定义页面样式引用，可以为首页、单数页、偶数页定义不同的页面风格-->
            <fo:sequence-specification>
                <fo:sequence-specifier-alternating
                    page-master-first="main"
                    page-master-odd="main"
                    page-master-even="main"/>
            </fo:sequence-specification>
            <!--页眉显示内容-->
            <fo:static-content flow-name="xsl-region-before">
                <fo:block line-height="1pt" font-size="1pt" text-align="end"></fo:block>
            </fo:static-content>
            <!--页脚显示内容-->
            <fo:static-content flow-name="xsl-region-after">
            <fo:block line-height="10pt" font-size="10pt" text-align="center">第<fo:page-number/>页  共 2 页</fo:block>
            </fo:static-content>
            <!--页面主体内容-->
            <fo:flow flow-name="xsl-region-body">
                <!--报表头-->
                <xsl:apply-templates select="ReportHeader"/>
                <!--报表体(若有多个部分内容，参照下面一行重复)-->
                <xsl:apply-templates select="ReportBody"/>
                <!--报表尾-->
                <xsl:apply-templates select="ReportFooter"/>
            </fo:flow>
        </fo:page-sequence>
    </xsl:template>
    
    
          <!--报表主体（一般只有一个表格）//-->
     <xsl:template match="ReportBody">
        <!--空行,第一联和后两联不同,头联空1行,后两联空2行,有动态xml控制-->
        
        <xsl:apply-templates select="TableBegin"/>
        <xsl:apply-templates select="TableLetter"/>
        <xsl:apply-templates select="TableEnd"/>
       
      
        
      </xsl:template> 
      
      
      
      
     <xsl:template match="TableLetter"> 
     
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="left" text-indent="0px"  space-before="8pt" space-after="2pt"  line-height="17pt">鉴于：</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">一、投资接受人经重庆投资无忧金融服务有限公司(以下简称投资无忧金融)接受注册，意向投资人融资,并承诺到期无条件回购投资人的投资权益（包含本金及收益）。担保人就投资接受人向投资人回购投资权益的行为和责任提供履约连带责任担保；</fo:block>     
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">二、担保人是根据中华人民共和国法律而合法存在的法人，根据《中华人民共和国担保法》等法律及有关法规的规定，具有提供保证担保的法律资格；</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">三、担保人在出具本担保函时，已就其财务状况及涉及的仲裁、诉讼等情况进行了充分披露，具有代表投资接受人向投资人履约的能力。</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">本担保人出于真实意思，在此承诺对投资人此次投资形成的权益（包含本金和收益）的到期履约兑付提供无条件的不可撤销的连带责任保证担保。具体担保事宜如下：</fo:block>


      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第一条 被担保的投资权益数额</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">被担保的投资权益为：人民币 <xsl:value-of select="DMONEY"/> 万元，年化收益率为 <xsl:value-of select="PER"/>  %。</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">除本条前述所列明的权益外，担保人不对投资人的其他权益承担担保责任。</fo:block>
      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第二条 投资期间</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">投资期间为 <xsl:value-of select="SYYYY"/> 年 <xsl:value-of select="SMM"/> 月 <xsl:value-of select="SDD"/>日—  <xsl:value-of select="EYYYY"/> 年 <xsl:value-of select="EMM"/> 月 <xsl:value-of select="EDD"/>日。投资接受人应于到期日回购本金和收益，如遇法定节假日或休息日，则顺延至其后的第一个工作日，顺延期间不另计息。</fo:block>
 
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第三条 保证的方式</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">担保人承担保证的方式为连带责任保证。</fo:block>
      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第四条 保证责任的承担</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">如投资接受人不能按照《投资权益回购履约担保合同》之规定即在本担保函项下的投资权益在到期日足额支付相应本金及收益时，担保人在收到《代偿通知书》后于 3 个工作日内将代偿款足额划入担保人在投资无忧金融服务平台关联的易极付账户绑定的银行账户中，并完成全部代偿相关手续。 </fo:block>
 
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第五条 保证范围</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">担保人保证的范围包括《投资权益回购履约担保合同》项下的本金及收益，相关合同编号为：<xsl:value-of select="CONTRACTNO"/></fo:block>
 
 	  <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第六条 保证的期间</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">担保人承担保证责任的期间为投资收益（含本金及收益）回购到期之日起二年。</fo:block>
      
      
      <fo:block  font-weight="bold" wrap-option="wrap" language="zh" text-align="center" text-indent="0px"  space-before="10pt" space-after="2pt"  line-height="17pt">第七条 担保函的生效</fo:block>
      <fo:block  wrap-option="wrap" language="zh" text-align="left" text-indent="20px"  space-before="8pt" space-after="2pt"  line-height="17pt">本担保函自签订之日生效，至保证期间届满之日失效。</fo:block>
     
    </xsl:template>   
      

    
    
    <xsl:template match="TableBegin">
    	<fo:table space-before.optimum="10pt" border-collapse="separate" font-size="10pt"  text-align="right"  border-width="0pt" border-style="solid" border-color="yellow">
	   <fo:table-column column-width="2cm"/>
	   <fo:table-column column-width="8cm"/>
	   <fo:table-column column-width="4cm"/>
	   <fo:table-column column-width="3cm"/>
       <!-- 定义表头 //-->
         <fo:table-header>
	     <fo:table-row height="10pt">
	         <fo:table-cell number-columns-spanned="2"  height="10pt">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	          <fo:table-cell >
	             <fo:block wrap-option="wrap">编号：</fo:block>
	         </fo:table-cell>
	          <fo:table-cell >
	             <fo:block text-align="center"  wrap-option="wrap"><xsl:value-of select="LETTERNO"/></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     </fo:table-header>
	     
	     <fo:table-body  text-align="left"  >
	     
	     
	     
	         <fo:table-row height="15pt">
		          <fo:table-cell  number-columns-spanned="4" >
		             <fo:block wrap-option="wrap" ></fo:block>
		          </fo:table-cell>
	          </fo:table-row>
	     
	     
	          <fo:table-row height="30pt">
		          <fo:table-cell number-columns-spanned="4"  height="20pt" >
		             <fo:block text-align="center"  wrap-option="wrap"  font-weight="bold" font-size="20pt">担 保 函</fo:block>
		          </fo:table-cell>
	          </fo:table-row>
	          
	          <fo:table-row height="15pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" >担保人：</fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" ><xsl:value-of select="ENTERPRISE"/> </fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	          <fo:table-row height="15pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" >住  所：</fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" ><xsl:value-of select="COMADDRESS"/></fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	           <fo:table-row height="15pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" >邮政编码：</fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" ><xsl:value-of select="ZIPCODE"/></fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	           <fo:table-row height="15pt">
		          <fo:table-cell  >
		             <fo:block wrap-option="wrap" >法定代表人：</fo:block>
		          </fo:table-cell>
		          <fo:table-cell  >
		             <fo:block  wrap-option="wrap" ><xsl:value-of select="LEGALNAME"/></fo:block>
		          </fo:table-cell>
		          <fo:table-cell >
		             <fo:block  number-columns-spanned="2"  ></fo:block>
		         </fo:table-cell>
	          </fo:table-row>
	          
	         
         </fo:table-body>
      </fo:table>
   </xsl:template>
      
      
    <xsl:template match="TableEnd">
    
     <fo:table space-before.optimum="10pt" border-collapse="separate" font-size="10pt"  text-align="center"  border-width="0pt" border-style="solid" border-color="green">
	   <fo:table-column column-width="5cm"/>
	   <fo:table-column column-width="7cm"/>
	   <fo:table-column column-width="5cm"/>
       <!-- 定义表头 //-->
         <fo:table-header>
	     <fo:table-row height="20pt">
	         <fo:table-cell   height="100pt"   number-columns-spanned="3">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	      <fo:table-row height="10pt">
	         <fo:table-cell    >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap">担保人：<xsl:value-of select="ENTERPRISE"/>（公章）</fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	       <fo:table-row height="20pt">
	         <fo:table-cell   height="30pt"   number-columns-spanned="3">
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	      <fo:table-row >
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="center"  wrap-option="wrap"></fo:block>
	         </fo:table-cell>
	         <fo:table-cell  >
	             <fo:block text-align="right"  wrap-option="wrap" ><xsl:value-of select="PYYYY"/>年<xsl:value-of select="PMM"/> 月<xsl:value-of select="PDD"/> 日</fo:block>
	         </fo:table-cell>
	     </fo:table-row>
	     
	     </fo:table-header>
	     <fo:table-body>
         </fo:table-body>
      </fo:table>
    </xsl:template>  
       
       
      <xsl:template match="ReportFooter">
       <fo:block id="endofdoc"></fo:block>
      </xsl:template>   
  
  
  </xsl:stylesheet>  
    