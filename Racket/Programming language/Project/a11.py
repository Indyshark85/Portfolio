import sys
from enum import Enum
from greenlet import greenlet
from typing import Any, Callable

# Define the program counter
pc : Callable[[], Any] = None

# Define the registers
_e_: object = None
_env_: object = None
_k_: object = None
_v_: object = None
_y_: object = None
_clos_: object = None
_a_: object = None
_r_cap_: object = None
_env_cap_: object = None
_k_cap_: object = None
_lv_cap_: object = None
_f_cap_: object = None
_rand_cap_: object = None
_body_cap_: object = None
_conseq_cap_: object = None
_alt_cap_: object = None
_result_cap_: object = None
_kr2_cap_: object = None

# Define the dismount greenlet
_dismount_blank = None

# Define the union classes
class union_t(object):
    class expr(Enum):
        const = 0
        dBvar = 1
        mult = 2
        decr = 3
        zerop = 4
        dBlam = 5
        app = 6
        dBlocal = 7
        ifte = 8
        dBcatch = 9
        throw = 10

    class clos(Enum):
        lam = 0

    class envr(Enum):
        empty = 0
        extend = 1

    class kt(Enum):
        empty = 0
        mult_left = 1
        mult_right = 2
        decr = 3
        zerop = 4
        ifte = 5
        app_rator = 6
        app_rand = 7
        local = 8
        throw = 9
        throw_result = 10

    class expr(Enum):
        const = 0
        dBvar = 1
        mult = 2
        decr = 3
        zerop = 4
        dBlam = 5
        app = 6
        dBlocal = 7
        ifte = 8
        dBcatch = 9
        throw = 10

    class clos(Enum):
        lam = 0

    class envr(Enum):
        empty = 0
        extend = 1

    class kt(Enum):
        empty = 0
        mult_left = 1
        mult_right = 2
        decr = 3
        zerop = 4
        ifte = 5
        app_rator = 6
        app_rand = 7
        local = 8
        throw = 9
        throw_result = 10

    def __init__(self, type: Enum, **kwargs):
        self.type = type
        for key in kwargs:
            setattr(self, key, kwargs[key])


# Union functions
def kt_empty(dismount):
    return union_t(union_t.kt.empty, 
            dismount=dismount)

def kt_mult_left(r_cap, env_cap, k_cap):
    return union_t(union_t.kt.mult_left, 
            r_cap=r_cap, 
            env_cap=env_cap, 
            k_cap=k_cap)

def kt_mult_right(lv_cap, k_cap):
    return union_t(union_t.kt.mult_right, 
            lv_cap=lv_cap, 
            k_cap=k_cap)

def kt_decr(k_cap):
    return union_t(union_t.kt.decr, 
            k_cap=k_cap)

def kt_zerop(k_cap):
    return union_t(union_t.kt.zerop, 
            k_cap=k_cap)

def kt_ifte(conseq_cap, alt_cap, env_cap, k_cap):
    return union_t(union_t.kt.ifte, 
            conseq_cap=conseq_cap, 
            alt_cap=alt_cap, 
            env_cap=env_cap, 
            k_cap=k_cap)

def kt_app_rator(rand_cap, env_cap, k_cap):
    return union_t(union_t.kt.app_rator, 
            rand_cap=rand_cap, 
            env_cap=env_cap, 
            k_cap=k_cap)

def kt_app_rand(f_cap, k_cap):
    return union_t(union_t.kt.app_rand, 
            f_cap=f_cap, 
            k_cap=k_cap)

def kt_local(body_cap, env_cap, k_cap):
    return union_t(union_t.kt.local, 
            body_cap=body_cap, 
            env_cap=env_cap, 
            k_cap=k_cap)

def kt_throw(result_cap, env_cap):
    return union_t(union_t.kt.throw, 
            result_cap=result_cap, 
            env_cap=env_cap)

def kt_throw_result(kr2_cap):
    return union_t(union_t.kt.throw_result, 
            kr2_cap=kr2_cap)

def envr_empty():
    return union_t(union_t.envr.empty, )

def envr_extend(a_cap, env_cap):
    return union_t(union_t.envr.extend, 
            a_cap=a_cap, 
            env_cap=env_cap)

def clos_lam(body, env):
    return union_t(union_t.clos.lam, 
            body=body, 
            env=env)

def expr_const(value):
    return union_t(union_t.expr.const, 
            value=value)

def expr_dBvar(index):
    return union_t(union_t.expr.dBvar, 
            index=index)

def expr_mult(l, r):
    return union_t(union_t.expr.mult, 
            l=l, 
            r=r)

def expr_decr(r):
    return union_t(union_t.expr.decr, 
            r=r)

def expr_zerop(r):
    return union_t(union_t.expr.zerop, 
            r=r)

def expr_dBlam(body):
    return union_t(union_t.expr.dBlam, 
            body=body)

def expr_app(rator, rand):
    return union_t(union_t.expr.app, 
            rator=rator, 
            rand=rand)

def expr_dBlocal(rhs, body):
    return union_t(union_t.expr.dBlocal, 
            rhs=rhs, 
            body=body)

def expr_ifte(test, conseq, alt):
    return union_t(union_t.expr.ifte, 
            test=test, 
            conseq=conseq, 
            alt=alt)

def expr_dBcatch(body):
    return union_t(union_t.expr.dBcatch, 
            body=body)

def expr_throw(continuation, result):
    return union_t(union_t.expr.throw, 
            continuation=continuation, 
            result=result)

# Generate functions
def value_of_cps():
    global pc
    global pc
    global _e_
    global _k_
    global _env_
    global _v_
    global _y_

    match _e_.type:
        case union_t.expr.const:
            v = _e_.value
            _v_ = v
            pc = apply_k

        case union_t.expr.dBvar:
            y = _e_.index
            _y_ = y
            pc = apply_env

        case union_t.expr.mult:
            l = _e_.l
            r = _e_.r
            _k_ = kt_mult_left(r, _env_, _k_)
            _e_ = l
            pc = value_of_cps

        case union_t.expr.decr:
            r = _e_.r
            _k_ = kt_decr(_k_)
            _e_ = r
            pc = value_of_cps

        case union_t.expr.zerop:
            r = _e_.r
            _k_ = kt_zerop(_k_)
            _e_ = r
            pc = value_of_cps

        case union_t.expr.dBlam:
            body = _e_.body
            _v_ = clos_lam(body, _env_)
            pc = apply_k

        case union_t.expr.app:
            rator = _e_.rator
            rand = _e_.rand
            _k_ = kt_app_rator(rand, _env_, _k_)
            _e_ = rator
            pc = value_of_cps

        case union_t.expr.dBlocal:
            rhs = _e_.rhs
            body = _e_.body
            _k_ = kt_local(body, _env_, _k_)
            _e_ = rhs
            pc = value_of_cps

        case union_t.expr.ifte:
            test = _e_.test
            conseq = _e_.conseq
            alt = _e_.alt
            _k_ = kt_ifte(conseq, alt, _env_, _k_)
            _e_ = test
            pc = value_of_cps

        case union_t.expr.dBcatch:
            body = _e_.body
            _env_ = envr_extend(_k_, _env_)
            _e_ = body
            pc = value_of_cps

        case union_t.expr.throw:
            continuation = _e_.continuation
            result = _e_.result
            _k_ = kt_throw(result, _env_)
            _e_ = continuation
            pc = value_of_cps

def apply_k():
    global pc
    global pc
    global _k_
    global _v_
    global _env_
    global _e_
    global _a_
    global _clos_
    global _f_cap_
    global _lv_cap_

    match _k_.type:
        case union_t.kt.empty:
            dismount = _k_.dismount
            dismount.switch()

        case union_t.kt.mult_left:
            r_cap = _k_.r_cap
            env_cap = _k_.env_cap
            kr2_cap = _k_.k_cap
            _lv_cap_ = _v_
            _k_ = kt_mult_right(_lv_cap_, kr2_cap)
            _e_ = r_cap
            _env_ = env_cap
            pc = value_of_cps

        case union_t.kt.mult_right:
            lv_cap = _k_.lv_cap
            kr2_cap = _k_.k_cap
            _v_ = lv_cap * _v_
            _k_ = kr2_cap
            pc = apply_k

        case union_t.kt.decr:
            kr2_cap = _k_.k_cap
            _v_ = (_v_ - 1)
            _k_ = kr2_cap
            pc = apply_k

        case union_t.kt.zerop:
            kr2_cap = _k_.k_cap
            _v_ = (_v_ == 0)
            _k_ = kr2_cap
            pc = apply_k

        case union_t.kt.ifte:
            conseq_cap = _k_.conseq_cap
            alt_cap = _k_.alt_cap
            env_cap = _k_.env_cap
            kr2_cap = _k_.k_cap
            if _v_:
                _e_ = conseq_cap
                _env_ = env_cap
                _k_ = kr2_cap
                pc = value_of_cps
            else:
                _e_ = alt_cap
                _env_ = env_cap
                _k_ = kr2_cap
                pc = value_of_cps

        case union_t.kt.app_rator:
            rand_cap = _k_.rand_cap
            env_cap = _k_.env_cap
            kr2_cap = _k_.k_cap
            _f_cap_ = _v_
            _k_ = kt_app_rand(_f_cap_, kr2_cap)
            _e_ = rand_cap
            _env_ = env_cap
            pc = value_of_cps

        case union_t.kt.app_rand:
            f_cap = _k_.f_cap
            kr2_cap = _k_.k_cap
            _clos_ = f_cap
            _a_ = _v_
            _k_ = kr2_cap
            pc = apply_closure

        case union_t.kt.local:
            body_cap = _k_.body_cap
            env_cap = _k_.env_cap
            kr2_cap = _k_.k_cap
            _e_ = body_cap
            _env_ = envr_extend(_v_, env_cap)
            _k_ = kr2_cap
            pc = value_of_cps

        case union_t.kt.throw:
            result_cap = _k_.result_cap
            env_cap = _k_.env_cap
            _e_ = result_cap
            _env_ = env_cap
            _k_ = _v_
            pc = value_of_cps

        case union_t.kt.throw_result:
            kr2_cap = _k_.kr2_cap
            _k_ = kr2_cap
            pc = apply_k

def apply_closure():
    global pc
    global pc
    global _env_
    global _a_
    global _e_

    match _clos_.type:
        case union_t.clos.lam:
            body = _clos_.body
            env = _clos_.env
            _e_ = body
            _env_ = envr_extend(_a_, env)
            pc = value_of_cps

def apply_env():
    global pc
    global pc
    global _y_
    global _env_
    global _v_

    match _env_.type:
        case union_t.envr.empty:
            raise RuntimeError("unbound variable")

        case union_t.envr.extend:
            a_cap = _env_.a_cap
            env_cap = _env_.env_cap
            if (_y_ == 0):
                _v_ = a_cap
                pc = apply_k
            else:
                _env_ = env_cap
                _y_ = (_y_ - 1)
                pc = apply_env

def mount_tram():
    global pc
    global no_dis
    global _dismount_blank
    no_dis= no_const(_dismount_blank)

    while True:
        greenlet(pc).switch()


