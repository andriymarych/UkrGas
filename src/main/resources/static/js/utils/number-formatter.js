
const formatFractionalPart = (number) =>{
    return  number % 1 === 0 ?  number + '.00' : number;

}
export {formatFractionalPart}