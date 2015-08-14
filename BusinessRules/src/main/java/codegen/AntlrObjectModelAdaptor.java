package codegen;

import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.misc.ObjectModelAdaptor;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AntlrObjectModelAdaptor implements ModelAdaptor {

    protected static final Member INVALID_MEMBER;
    protected static final Map<Class<?>, Map<String, Member>> membersCache;

    static {
        Field invalidMember;
        try {
            invalidMember = ObjectModelAdaptor.class.getDeclaredField("INVALID_MEMBER");
        } catch (NoSuchFieldException var2) {
            invalidMember = null;
        } catch (SecurityException var3) {
            invalidMember = null;
        }

        INVALID_MEMBER = invalidMember;
        membersCache = new HashMap();
    }

    protected static Member findMember(Class<?> clazz, String memberName) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        } else if (memberName == null) {
            throw new NullPointerException("memberName");
        } else {
            Map var2 = membersCache;
            synchronized (membersCache) {
                Object members = (Map) membersCache.get(clazz);
                Member member = null;
                if (members != null) {
                    member = (Member) ((Map) members).get(memberName);
                    if (member != null) {
                        return member != INVALID_MEMBER ? member : null;
                    }
                } else {
                    members = new HashMap();
                    membersCache.put(clazz, (Map<String, Member>) members);
                }

                String methodSuffix = Character.toUpperCase(memberName.charAt(0)) + memberName.substring(1, memberName.length());
                Object member1 = tryGetMethod(clazz, "get" + methodSuffix);
                if (member1 == null) {
                    member1 = tryGetMethod(clazz, "is" + methodSuffix);
                    if (member1 == null) {
                        member1 = tryGetMethod(clazz, "has" + methodSuffix);
                        if (member1 == null) {
                            member1 = tryGetMethod(clazz, memberName);
                        }
                    }
                }

                if (member1 == null) {
                    member1 = tryGetField(clazz, memberName);
                }

                ((Map) members).put(memberName, member1 != null ? member1 : INVALID_MEMBER);
                return (Member) member1;
            }
        }
    }


    protected static Method tryGetMethod(Class<?> clazz, String methodName) {
        try {
            Method ex = clazz.getMethod(methodName, new Class[0]);
            if (ex != null) {
                ex.setAccessible(true);
            }

            return ex;
        } catch (NoSuchMethodException var3) {
            ;
        } catch (SecurityException var4) {
            ;
        }

        return null;
    }

    protected static Field tryGetField(Class<?> clazz, String fieldName) {
        try {
            Field ex = clazz.getField(fieldName);
            if (ex != null) {
                ex.setAccessible(true);
            }

            return ex;
        } catch (NoSuchFieldException var3) {
            ;
        } catch (SecurityException var4) {
            ;
        }

        return null;
    }

    public synchronized Object getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {
        System.err.printf("GET %s %s.%s ", self.getName(), o.getClass().getSimpleName(), propertyName);
        Object value = _getProperty(interp, self, o, property, propertyName);
        System.err.printf(" VAL = %s \n", (value == null ? "NULL" : value.toString()));
        return value;
    }

    private Object _getProperty(Interpreter interp, ST self, Object o, Object property, String propertyName) throws STNoSuchPropertyException {


        if (o == null) {
            throw new NullPointerException("o");
        } else {
            Class c = o.getClass();
            if (property == null) {
                return this.throwNoSuchProperty(c, propertyName, (Exception) null);
            } else {
                Member member = findMember(c, propertyName);
                if (member != null) {
                    try {
                        if (member instanceof Method) {
                            return ((Method) member).invoke(o, new Object[0]);
                        }

                        if (member instanceof Field) {
                            return ((Field) member).get(o);
                        }
                    } catch (Exception var9) {
                        this.throwNoSuchProperty(c, propertyName, var9);
                    }
                }

                return this.throwNoSuchProperty(c, propertyName, (Exception) null);
            }
        }

    }

    protected Object throwNoSuchProperty(Class<?> clazz, String propertyName, Exception cause) {
        throw new STNoSuchPropertyException(cause, (Object) null, clazz.getName() + "." + propertyName);
    }
}



