package org.gonnaup.examples.springs.aop.aspectj;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法运行时长统计aspect
 * 切点函数：
 * <table style="border:1px solid">
 *   <tbody><tr>
 *     <th>类别</th>
 *     <th>函数</th>
 *     <th>入参</th>
 *     <th>说明</th>
 *   </tr>
 *
 *   <tr>
 *     <td rowspan="2">方法切点函数</td>
 *     <td>execution()</td>
 *     <td>方法匹配模式串</td>
 *     <td>表示满足某一匹配模式的所有目标类方法连接点。如execution(* greetTo(..))表示所有目标类中的greetTo()方法。</td>
 *   </tr>
 *
 *   <tr>
 *     <td>@annotation()</td>
 *     <td>方法注解类名</td>
 *     <td>表示标注了特定注解的目标方法连接点。如@annotation(com.baobaotao.anno.NeedTest)表示任何标注了@NeedTest注解的目标类方法。</td>
 *   </tr>
 *
 *   <tr>
 *     <td rowspan="2">方法入参切点函数</td>
 *     <td>args()</td>
 *     <td>类名</td>
 *     <td>通过判别目标类方法运行时入参对象的类型定义指定连接点。如args(com.baobaotao.Waiter)表示所有有且仅有一个按类型匹配于Waiter的入参的方法。</td>
 *   </tr>
 *
 *   <tr>
 *     <td>@args()</td>
 *     <td>类型注解类名</td>
 *     <td>通过判别目标方法的运行时入参对象的类是否标注特定注解来指定连接点。如@args(com.baobaotao.Monitorable)表示任何这样的一个目标方法：它有一个入参且入参对象的类标注@Monitorable注解。</td>
 *   </tr>
 *
 *   <tr>
 *     <td rowspan="4">目标类切点函数</td>
 *     <td>within()</td>
 *     <td>类名匹配串</td>
 *     <td>&nbsp; 表示特定域下的所有连接点。如within(com.baobaotao.service.*)表示com.baobaotao.service包中的所有连接点，也即包中所有类的所有方法，而within(com.baobaotao.service.*Service)表示在com.baobaotao.service包中，所有以Service结尾的类的所有连接点。</td>
 *   </tr>
 *
 *   <tr>
 *     <td>target()</td>
 *     <td>类名</td>
 *     <td>假如目标类按类型匹配于指定类，则目标类的所有连接点匹配这个切点。如通过target(com.baobaotao.Waiter)定义的切点，Waiter、以及Waiter实现类NaiveWaiter中所有连接点都匹配该切点。</td>
 *   </tr>
 *
 *   <tr>
 *     <td>@within()</td>
 *     <td>类型注解类名</td>
 *     <td>假如目标类按类型匹配于某个类A，且类A标注了特定注解，则目标类的所有连接点匹配这个切点。如@within(com.baobaotao.Monitorable)定义的切点，假如Waiter类标注了@Monitorable注解，则Waiter以及Waiter实现类NaiveWaiter类的所有连接点都匹配。</td>
 *   </tr>
 *
 *   <tr>
 *     <td>@target()</td>
 *     <td>类型注解类名</td>
 *     <td>  目标类标注了特定注解，则目标类所有连接点匹配该切点。如@target(com.baobaotao.Monitorable)，假如NaiveWaiter标注了@Monitorable，则NaiveWaiter所有连接点匹配切点。</td>
 *   </tr>
 *
 *   <tr>
 *     <td>代理类切点函数</td>
 *     <td>this()</td>
 *     <td>类名</td>
 *     <td>代理类按类型匹配于指定类，则被代理的目标类所有连接点匹配切点。限制连接点匹配 AOP 代理的 Bean 引用为指定类型的类</td>
 *   </tr>
 *
 * </tbody></table>
 *
 *  切点运算符：<code>&&，||，!</code>
 * @author gonnaup
 * @version created at 2021/8/9 11:57
 */
@Slf4j
@Aspect
public class TimeSpentAspect {

    /**
     * <a href="https://blog.csdn.net/kkdelta/article/details/7441829">execution()</a>：方法匹配模式串，表示满足某一匹配模式下所有目标类方法连接点
     * <p>
     *     <ul>
     *         <li>execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>)</li>
     *         <li>*：匹配任意字符，但它只能匹配上下文中的一个元素</li>
     *         <li>..：匹配任意字符，可以匹配上下文中的多个元素，但在表示类时，必须和*联合使用，而在表示入参时则单独使用</li>
     *     </ul>
     * </p>
     *
     */
    @Pointcut("execution(public * org.gonnaup.examples..OrderService.*(..))")
    public void orderPointcut() {
    }

    /**
     * <code>@annotation：</code>方法注解类名
     */
    @Pointcut("@annotation(org.gonnaup.examples.springs.aop.aspectj.TimeSpentAspect.CalculateTimeSpent)")
    public void annotationPointcut() {
    }

    @Around("orderPointcut() || annotationPointcut()")
    public Object orderTimeSpentAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        TimeSpenter timeSpenter = TimeSpenter.create();
        Object proceed = joinPoint.proceed();
        log.info("执行方法 {}#{} 耗时 {}ms", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), timeSpenter.stop().timeSpent());
        return proceed;

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    public @interface CalculateTimeSpent {
    }


}
