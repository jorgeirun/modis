      real*8 function own_dclb(calval, pente, offset)
      real*8 calval, pente, offset
      own_dclb = pente*calval + offset
      end
